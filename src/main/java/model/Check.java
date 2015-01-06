package model;

import java.util.List;

/**
 * Created by emile on 06/01/15.
 */
public class Check {
    String depuis;
    String vers;
    String pushbulletApiKey;
    List<Filter> Filters;

    public String getDepuis() {
        return depuis;
    }

    public void setDepuis(String depuis) {
        this.depuis = depuis;
    }

    public String getVers() {
        return vers;
    }

    public void setVers(String vers) {
        this.vers = vers;
    }

    public String getPushbulletApiKey() {
        return pushbulletApiKey;
    }

    public void setPushbulletApiKey(String pushbulletApiKey) {
        this.pushbulletApiKey = pushbulletApiKey;
    }

    public List<Filter> getFilters() {
        return Filters;
    }

    public void setFilters(List<Filter> filters) {
        Filters = filters;
    }
}
