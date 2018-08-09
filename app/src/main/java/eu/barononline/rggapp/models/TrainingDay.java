package eu.barononline.rggapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainingDay {

	private int dayOfWeek;
	private ArrayList<Training> trainings = new ArrayList<>();

	public TrainingDay(JSONObject obj) {
		try {
			dayOfWeek = obj.getInt("dow");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			JSONArray times = obj.getJSONArray("times");
			for(int i = 0; i < times.length(); i++) {
				try {
					trainings.add(new Training(times.getJSONObject(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public TrainingDay(int dayOfWeek, Training[] times) {
		this.dayOfWeek = dayOfWeek;
		trainings.addAll(Arrays.asList(times));
	}

	public Training[] getTrainings() {
		return trainings.toArray(new Training[0]);
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}
}
