package eu.barononline.rggapp.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Time;
import java.util.GregorianCalendar;

public class TrainingWeek {

	private TrainingDay[] trainingDays;

	public TrainingWeek(JSONArray arr) {
		trainingDays = new TrainingDay[arr.length()];

		for(int i = 0; i < arr.length(); i++) {
			try {
				trainingDays[i] = new TrainingDay(arr.getJSONObject(i));
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
				if(training.getEndTime().getTime().compareTo(latest.getTime()) > 0) {
					latest = training.getEndTime();
				}
			}
		}

		return latest;
	}
}
