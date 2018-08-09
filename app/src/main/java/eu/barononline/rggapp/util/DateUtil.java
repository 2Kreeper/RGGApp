package eu.barononline.rggapp.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {

	public static float getHours(GregorianCalendar date) {
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minutes = date.get(Calendar.MINUTE);
		int seconds = date.get(Calendar.SECOND);

		return hours + (minutes / 60f) + (seconds / 60f / 60f);
	}
}
