package eu.barononline.rggapp.models;

import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Time;
import java.util.GregorianCalendar;

import eu.barononline.rggapp.models.training.Training;

public class TrainingWeek {

	private TrainingDay[] trainingDays;

	public TrainingWeek(JSONArray arr, Resources resources) {
		trainingDays = new TrainingDay[arr.length()];

		for(int i = 0; i < arr.length(); i++) {
			try {
				trainingDays[i] = new TrainingDay(arr.getJSONObject(i), resources);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public TrainingWeek(TrainingDay[] trainingDays) {
		this.trainingDays = trainingDays;
	}

	public TrainingDay[] getTrainingDays() {
		return trainingDays.clone();
	}

	public GregorianCalendar getEarliestTime() {
		GregorianCalendar earliest = new GregorianCalendar(9999, 12, 31, 23, 59, 59);

		for(TrainingDay day : trainingDays) {
			for(Training training : day.getTrainings()) {
				if(training.getStartTime().getTime().compareTo(earliest.getTime()) < 0) {
					earliest = training.getStartTime();
				}
			}
		}

		return earliest;
	}

	public GregorianCalendar getLatestTime() {
		GregorianCalendar latest = new GregorianCalendar(0, 0, 0, 0, 0, 0);

		for(TrainingDay day : trainingDays) {
			for(Training training : day.getTrainings()) {
				if(training.getStartTime().getTime().compareTo(latest.getTime()) > 0) {
					latest = training.getEndTime();
				}
			}
		}

		return latest;
	}
}
