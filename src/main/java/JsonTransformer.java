import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

/**
 * Created by emile on 06/01/15.
 */
public class JsonTransformer implements ResponseTransformer {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String render(Object model) throws Exception {
        return mapper.writeValueAsString(model);
    }
}
