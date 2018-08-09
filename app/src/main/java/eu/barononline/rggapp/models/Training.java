package eu.barononline.rggapp.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;

import eu.barononline.rggapp.views.TrainingDrawable;

public class Training {

	private GregorianCalendar startTime, endTime;
	private String trainer;
	private boolean enabled;
	private TrainingType type;

	private TrainingDrawable drawable;

	public Training(JSONObject obj) {
		GregorianCalendar from = new GregorianCalendar(), to = new GregorianCalendar();
		try {
			from.setTimeInMillis(obj.getLong("from"));
			to.setTimeInMillis(obj.getLong("to"));

			startTime = from;
			endTime = to;
			trainer = obj.getString("trainer");
			enabled = obj.getBoolean("enabled");
			type = Enum.valueOf(TrainingType.class, obj.getString("training_type"));

			init();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Training(GregorianCalendar startTime, GregorianCalendar endTime, String trainer, boolean enabled, TrainingType type) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.trainer = trainer;
		this.enabled = enabled;
		this.type = type;

		init();
	}

	private void init() {
		drawable = new TrainingDrawable(this);
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

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public boolean isEnabled() {
		return enabled;
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
		Supervision(Color.BLUE), Regatta(Color.RED), Beginner(Color.GREEN);

		private Paint paint;

		TrainingType(int color) {
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(color);
		}

		public Paint getPaint() {
			return paint;
		}
	}
}
