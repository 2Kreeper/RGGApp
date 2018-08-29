package eu.barononline.rggapp.models.events;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

    public Event(JSONObject object) throws JSONException {
        this(object.getString("title"), object.getString("location"), object.getString("time"));
    }

    public Event(String title, String location, String timezone) {
        this.title = title;
        this.location = location;
        this.time = timezone;
    }

    public String title;
    public String location;
    public String time;
}
