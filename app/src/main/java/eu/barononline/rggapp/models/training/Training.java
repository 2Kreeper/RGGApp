package eu.barononline.rggapp.models.training;

import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import eu.barononline.rggapp.Constants;
import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.Trainer;
import eu.barononline.rggapp.views.TrainingDrawable;

public class Training {

	private GregorianCalendar startTime, endTime;
	private Trainer trainer;
	private boolean enabled;
	private int dayOfWeek;
	private TrainingType type;

	private TrainingDrawable drawable;

	public Training(JSONObject obj, Resources resources) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_DATE_PATTERN);
			Date fromDate = dateFormat.parse(obj.getString("startTime")), toDate = dateFormat.parse(obj.getString("endTime"));
			GregorianCalendar from = new GregorianCalendar(), to = new GregorianCalendar();

			from.setTimeInMillis(fromDate.getTime());
			to.setTimeInMillis(toDate.getTime());

			startTime = from;
			endTime = to;
			trainer = new Trainer(obj.getJSONObject("trainer"));
			enabled = obj.getBoolean("enabled");
			type = Enum.valueOf(TrainingType.class, obj.getString("type"));
			dayOfWeek = obj.getInt("dayOfWeek");

			init(resources);
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}
	}

	public Training(GregorianCalendar startTime, GregorianCalendar endTime, Trainer trainer, boolean enabled, TrainingType type, int dayOfWeek, Resources resources) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.trainer = trainer;
		this.enabled = enabled;
		this.type = type;
		this.dayOfWeek = dayOfWeek;

		init(resources);
	}

	private void init(Resources resources) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			drawable = new TrainingDrawable(this, resources, MainActivity.getAppTheme());
		} else {
			drawable = new TrainingDrawable(this, resources);
		}
	}

	public JSONObject toJson() {
		try {
			return new JSONObject()
					.put("from", startTime.getTime().getTime())
					.put("to", endTime.getTime().getTime())
					.put("trainer", trainer)
					.put("enabled", enabled)
					.put("training_type", type.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		return toJson().toString();
	}

	public GregorianCalendar getStartTime() {
		return startTime;
	}

	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = startTime;
	}

	public GregorianCalendar getEndTime() {
		return endTime;
	}

	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = endTime;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public float getDurationInHours() {
		return (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 3600000;
	}

	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public TrainingDrawable getDrawable() {
		return drawable;
	}

	public enum TrainingType {
		Supervision(R.color.colorTrainingSupervision, R.string.trainging_supervision),
		Regatta(R.color.colorTrainingRegatta, R.string.trainging_regatta),
		Beginner(R.color.colorTrainingBeginners, R.string.trainging_beginner);

		private Paint paint;
		private Resources.Theme theme;
		private int color, localizedStringResource;

		TrainingType(int color, int localizedStringResource) {
			this.color = color;
			this.localizedStringResource = localizedStringResource;
		}

		public Paint getPaint(Resources resources) {
			if(paint == null) {
				paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(resources.getColor(color));
			}

			return paint;
		}

		@RequiresApi(api = Build.VERSION_CODES.M)
		public Paint getPaint(Resources resources, Resources.Theme theme) {
			if(paint == null || this.theme != theme) {
				this.theme = theme;
				paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(resources.getColor(color, theme));
			}

			return paint;
		}

		public String toLocalizedString(Resources resources) {
			return resources.getString(localizedStringResource);
		}
	}
}
