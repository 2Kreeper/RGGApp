package eu.barononline.rggapp.util;

import java.util.GregorianCalendar;

import eu.barononline.rggapp.models.training.Training;

public class TrainingUtil {

	public static GregorianCalendar getEarliestTime(Training[] trainings) {
		GregorianCalendar earliest = new GregorianCalendar(9999, 12, 31, 23, 23, 59);

		for(Training training : trainings) {
			if(training.getStartTime().getTimeInMillis() < earliest.getTimeInMillis()) {
				earliest = training.getStartTime();
			}
		}

		return earliest;
	}

	public static GregorianCalendar getLatestTime(Training[] trainings) {
		GregorianCalendar latest = new GregorianCalendar(0, 0, 0, 0, 0, 0);

		for(Training training : trainings) {
			if(training.getEndTime().getTimeInMillis() > latest.getTimeInMillis()) {
				latest = training.getEndTime();
			}
		}

		return latest;
	}
}
