package eu.barononline.rggapp.services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.models.events.Event;
import eu.barononline.rggapp.util.NetworkUtil;

public class EventListFetcher extends AsyncTask<String, String, Event[]> {

    private IReceiver<Event[]> receiver;

    public EventListFetcher(IReceiver<Event[]> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected Event[] doInBackground(String... strings) {
        //try {
            /*Event[] events = new Event[10];

            for (int i = 0; i < events.length; i++) {
                events[i] = generateEvent();
            }
            Log.v(MainActivity.LOG_TAG, "Result: " + NetworkUtil.sendRequestGET(NetworkUtil.BASE_URL + "events", ""));*/
        try {
            JSONArray eventsJSON = new JSONArray(NetworkUtil.sendRequestGET(NetworkUtil.BASE_URL + "events", ""));
            Event[] events = new Event[eventsJSON.length()];

            for(int i = 0; i < events.length; i++) {
                events[i] = new Event(eventsJSON.getJSONObject(i));
            }
            return events;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        /*} catch (JSONException e) {
            e.printStackTrace();
            return null;

        }*/
    }

    @Override
    protected void onPostExecute(Event[] events) {
        super.onPostExecute(events);
        receiver.onReceive(events);
    }

    private Event generateEvent() {
        Random random = new Random();
        return new Event(
                eventTitles[random.nextInt(eventTitles.length - 1)],
                eventLocations[random.nextInt(eventLocations.length - 1)],
                eventTimes[random.nextInt(eventTimes.length - 1)]
        );
    }

    private final String[] eventTitles = {"Regatta", "RWF", "Vorstandssitzung"};
    private final String[] eventLocations = {"Biggesee", "Bootshaus", "Gymnasium Gerresheim", "Köln"};
    private final String[] eventTimes = {"9.8.2018 17:00", "9 - 25 August"};
}
