package eu.barononline.rggapp.models.training;

import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainingDay {

	private int dayOfWeek;
	private ArrayList<Training> trainings = new ArrayList<>();
	private Resources resources;

	public TrainingDay(JSONObject obj, Resources resources) {
		this.resources = resources;
		try {
			dayOfWeek = obj.getInt("dow");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			JSONArray times = obj.getJSONArray("times");
			for(int i = 0; i < times.length(); i++) {
				try {
					trainings.add(new Training(times.getJSONObject(i), resources));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public TrainingDay(int dayOfWeek, Training[] times, Resources resources) {
		this.dayOfWeek = dayOfWeek;
		this.resources = resources;
		trainings.addAll(Arrays.asList(times));
	}

	public Training[] getTrainings() {
		return trainings.toArray(new Training[0]);
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}
}
