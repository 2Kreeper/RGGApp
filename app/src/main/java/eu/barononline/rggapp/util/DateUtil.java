package eu.barononline.rggapp.util;

import android.content.res.Resources;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import eu.barononline.rggapp.R;

public class DateUtil {

	public static float getTimeInHours(GregorianCalendar date) {
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minutes = date.get(Calendar.MINUTE);
		int seconds = date.get(Calendar.SECOND);

		return hours + (minutes / 60f) + (seconds / 60f / 60f);
	}

	public static String toTimeString(GregorianCalendar calendar, Resources resources) {
		return String.format(resources.getString(R.string.date_format),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND)
				);
	}
}
