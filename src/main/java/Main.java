/**
 * Created by emile on 05/01/15.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import model.Check;
import model.Filter;
import model.Gare;
import model.Train;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.SparkBase;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static spark.Spark.post;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SparkBase.port(8080);
        ObjectMapper mapper = new ObjectMapper();
        DateFormat railradarDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        DateFormat sncfDateFormat = new SimpleDateFormat("yyyy|MM|dd");
        DateFormat sncfTimeFormat = new SimpleDateFormat("HH|mm");
        mapper.setDateFormat(railradarDateFormat);
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Map<String, Set<Train>> notifications = ExpiringMap.builder()
                .expiration(3, TimeUnit.HOURS)
                .build();

        post("/check", (req, res) -> {
            logger.info("check begin with body {} ", req.body());
            Check check = mapper.readValue(req.body(), Check.class);
            res.type("application/json");
            HttpRequest reqDepuis = Unirest
                    .get("http://www.raildar.fr/json/gares")
                    .queryString("search", check.getDepuis());
            logger.info("Response from raildar: {}", reqDepuis.asString().getStatusText());
            HttpRequest reqVers = Unirest
                    .get("http://www.raildar.fr/json/gares")
                    .queryString("search", check.getVers());
            logger.info("Response from raildar: {}", reqVers.asString().getStatusText());
            Gare[] depuis = mapper.readValue(reqDepuis.asBinary().getBody(), Gare[].class);
            Gare[] vers = mapper.readValue(reqVers.asBinary().getBody(), Gare[].class);
            if (depuis.length == 1 && vers.length == 1) {
                String time = sncfTimeFormat.format(new Date());
                String date = sncfDateFormat.format(new Date());
                HttpRequest reqNProchainsTrains = Unirest
                        .get("https://www.kimonolabs.com/api/769se1sw")
                        .queryString("apikey", "ba06ae4c24d69e0f6c1c58452fda6d34")
                        .queryString("date_num_train", date)
                        .queryString("depart", "depart")
                        .queryString("cityDepart", depuis[0].getStoparea())
                        .queryString("destination", "destination")
                        .queryString("cityDestination", vers[0].getStoparea())
                        .queryString("train_horaire_depart", time)
                        .queryString("next_horaire", time);
                logger.info("Response from kimono: {}", reqNProchainsTrains.asString().getStatusText());
                List<Map> trains = JsonPath.read(reqNProchainsTrains.asString().getBody(), "$.results.trains");
                List<Train> resTrains = new ArrayList<Train>();
                for (Map map : trains) {
                    Train train = new Train();
                    String gare = (String) map.get("gares");
                    String[] splitGare = gare.split("\\n");
                    train.setGareDepart(splitGare[0]);
                    train.setGareArrivee(splitGare[1]);
                    String horaires = (String) map.get("horaires");
                    String[] splitHoraires = horaires.split(" \\n");
                    train.setHoraireDepart(splitHoraires[0]);
                    train.setHoraireArrivee(splitHoraires[1]);
                    Map trainMap = (Map) map.get("train");
                    train.setNumero((String) trainMap.get("text"));
                    train.setLien((String) trainMap.get("href"));
                    Map info = (Map) map.get("info");
                    if (((String) info.get("text")).contains("A l'heure")) {
                        train.setRetard(false);
                    } else {
                        train.setRetard(true);
                        train.setInfo((String) map.get("info"));
                    }
                    resTrains.add(train);
                }

                String pushbulletApiKey = check.getPushbulletApiKey();
                Set<Train> sentNotifications = notifications.get(pushbulletApiKey);
                if(sentNotifications == null){
                    sentNotifications = new HashSet<Train>();
                    notifications.put(pushbulletApiKey, sentNotifications);
                }

                //filters
                final Set<Train> finalSentNotifications = sentNotifications;
                List<Train> filteredTrains = resTrains.stream().filter(train -> {
                    // remove filtered
                    for (Filter filter : check.getFilters()) {
                        engine.put("train", train);
                        try {
                            boolean eval = (boolean) engine.eval(filter.getPredicate());
                            if (!eval) {
                                logger.info("Filtering removed train {} with predicate {}", train.getNumero(), filter.getPredicate());
                                return false;
                            }
                            logger.info("Filtering kept train {} with predicate {}", train.getNumero(), filter.getPredicate());
                        } catch (ScriptException e) {
                            logger.error("Scripting exception", e);
                        }
                    }
                    return true;
                }).filter(train -> {
                    // remove previously sent notification
                    if(finalSentNotifications.contains(train)){
                        // already sent, remove it
                        logger.info("Removed notification already sent {}", train.getNumero());
                        return false;
                    }else{
                        finalSentNotifications.add(train);
                        return true;
                    }
                }).collect(Collectors.toList());

                //pushbullet
                if (pushbulletApiKey != null) {
                    for (Train train : filteredTrains) {
                        HttpResponse<JsonNode> response = Unirest.post("https://api.pushbullet.com/v2/pushes")
                                .header("accept", "application/json")
                                .basicAuth(pushbulletApiKey, "")
                                .field("type", "link")
                                .field("title", "Train de " + train.getHoraireDepart() + " au depart de " + train.getGareDepart() + ": " + train.getInfo())
                                .field("url", train.getLien())
                                .field("body", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(train))
                                .asJson();
                        logger.info("Sent notification to pushbullet for train {}", train.getNumero());
                    }
                }
                return filteredTrains;
            } else {
                if (vers.length == 1) {
                    // return gares
                    return depuis;
                } else {
                    // return gares
                    return vers;
                }
            }
        }, new JsonTransformer());
    }
}