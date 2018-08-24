package eu.barononline.rggapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.training.Training;
import eu.barononline.rggapp.models.training.TrainingDay;
import eu.barononline.rggapp.models.training.TrainingWeek;
import eu.barononline.rggapp.services.IReceiver;
import eu.barononline.rggapp.services.TrainingWeekFetcher;
import eu.barononline.rggapp.util.DateUtil;

public class TrainingCalendarView extends View {

	private int width, height;
	private TrainingWeek trainingWeek;

	private int minHour, maxHour;
	private float trainingHeight, trainingWidth;
	private Paint hourLabelPaint, hourLineDividerPaint;

	private GestureDetectorCompat gestureDetector;
	private ArrayList<ITrainingTouchListener> trainingTouchListeners = new ArrayList<>();

	public TrainingCalendarView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		hourLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hourLabelPaint.setTextSize(50);
		hourLabelPaint.setColor(Color.BLACK);

		hourLineDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hourLineDividerPaint.setStrokeWidth(2);
		hourLineDividerPaint.setColor(Color.GRAY);

		gestureDetector = new GestureDetectorCompat(context, new GestureListener());

		refresh(null);
	}

	public void refresh(@Nullable Runnable onFinishedLoading) {
		new TrainingWeekFetcher(new IReceiver<TrainingWeek>() {
			@Override
			public void onReceive(@NonNull TrainingWeek obj) {
				setTrainingWeek(obj);
			}
		}, onFinishedLoading, getResources()).execute();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
		width = resolveSizeAndState(minw, widthMeasureSpec, 1);

		int minh = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
		height = resolveSizeAndState(minh, heightMeasureSpec, 0);

		setMeasuredDimension(width, height);

		calculateDrawSizes();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(trainingWeek == null)
			return;

		//hour labels + horizontal dividers
		for(int hour = minHour; hour < maxHour + 1; hour++) {
			float hourY = getHourDrawY(hour);

			canvas.drawText(Integer.toString(hour), getPaddingLeft(), hourY, hourLabelPaint);
			canvas.drawLine(getPaddingLeft() + hourLabelPaint.getTextSize() * 1.5f, hourY - (0.5f * hourLabelPaint.getTextSize()),
					width - getPaddingRight(), hourY - (0.5f * hourLabelPaint.getTextSize()), hourLineDividerPaint);
		}

		//day labels + vertical dividers
		String[] days = getResources().getStringArray(R.array.weekdays_short);
		for(int i = 0; i < 8; i++) {
			float x = i * trainingWidth + getPaddingLeft() + hourLabelPaint.getTextSize() * 2 - hourLineDividerPaint.getStrokeWidth();
			float startY = hourLabelPaint.getTextSize() / 2f + hourLabelPaint.getTextSize();

			canvas.drawLine(x, startY, x, height - (hourLabelPaint.getTextSize() / 2f), hourLineDividerPaint);
			try {
                String day = days[i];
                canvas.drawText(day, x + hourLabelPaint.getTextSize(), startY - hourLabelPaint.getTextSize() / 2f, hourLabelPaint);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

		drawTrainings(canvas, getPaddingLeft() + hourLabelPaint.getTextSize() * 2f - 1);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private void drawTrainings(Canvas canvas, float xOffset) {
		int dayIndex = 0;
		for(TrainingDay day : trainingWeek.getTrainingDays()) {
			for(Training training : day.getTrainings()) {
				training.getDrawable().onDraw(canvas, xOffset + dayIndex * trainingWidth,
						getHourDrawY(DateUtil.getTimeInHours(training.getStartTime())) - hourLabelPaint.getTextSize() * 0.5f,
						trainingWidth,
						getTrainingHeight(training));
			}

			dayIndex++;
		}
	}

	private void calculateDrawSizes() {
		if(trainingWeek == null)
			return;

		GregorianCalendar earliestTime = trainingWeek.getEarliestTime(), latestTime = trainingWeek.getLatestTime();

		minHour = earliestTime.get(Calendar.HOUR_OF_DAY);
		maxHour = latestTime.get(Calendar.HOUR_OF_DAY);
		if(latestTime.get(Calendar.MINUTE) > 0 || latestTime.get(Calendar.SECOND) > 0) {
			maxHour++;
		}

		int itemCount = (maxHour - minHour + 1);

		trainingHeight = (height - itemCount * hourLabelPaint.getTextSize() - hourLabelPaint.getTextSize()) / (itemCount - 1);
		trainingWidth = (width - getPaddingLeft() - hourLabelPaint.getTextSize() * 2) / trainingWeek.getTrainingDays().length;
	}

	private Training getAt(float x, float y) {
		for(TrainingDay day : trainingWeek.getTrainingDays()) {
			for(Training training : day.getTrainings()) {
				if(training.getDrawable().contains(x, y)) {
					return training;
				}
			}
		}

		return null;
	}

	private float getHourDrawY(float hour) {
		if(hour < minHour || hour > maxHour) {
			StringBuilder warn = new StringBuilder("Invalid hour: " + hour + "f\n");
			for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
				warn.append("\t\t" + element.toString() + "\n");
			}
			Log.w(MainActivity.LOG_TAG, warn.toString());
		}

		float i = hour - minHour;
		float y = (i + 1) * hourLabelPaint.getTextSize() + i * trainingHeight;

		return y  + hourLabelPaint.getTextSize();
	}

	private float getTrainingHeight(Training training) {
		//return (getHourDrawY(minHour + 1) - getHourDrawY(minHour)) * duration;
		return getHourDrawY(DateUtil.getTimeInHours(training.getEndTime())) -
				getHourDrawY(DateUtil.getTimeInHours(training.getStartTime()));
	}

	public TrainingWeek getTrainingWeek() {
		return trainingWeek;
	}

	public void setTrainingWeek(TrainingWeek trainingWeek) {
		this.trainingWeek = trainingWeek;
		calculateDrawSizes();

		invalidate();
	}

	public void addTrainingTouchListener(ITrainingTouchListener listener) {
		trainingTouchListeners.add(listener);
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		private Training selected;

		@Override
		public boolean onDown(MotionEvent e) {
			selected = getAt(e.getX(), e.getY());

			return selected != null;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if(selected != null) {
				for(ITrainingTouchListener listener : trainingTouchListeners) {
					listener.onTrainingTouch(selected, e);
				}
				return true;
			}
			return false;
		}
	}
}
