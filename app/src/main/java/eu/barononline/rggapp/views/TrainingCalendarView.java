package eu.barononline.rggapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.models.Training;
import eu.barononline.rggapp.models.TrainingDay;
import eu.barononline.rggapp.models.TrainingWeek;
import eu.barononline.rggapp.services.IReceiver;
import eu.barononline.rggapp.services.TrainingWeekFetcher;
import eu.barononline.rggapp.util.DateUtil;

public class TrainingCalendarView extends View {

	private int width, height;
	private TrainingWeek trainingWeek;

	private int minHour, maxHour;
	private float trainingHeight, trainingWidth;

	private Paint hourLabelPaint, hourLineDividerPaint;

	public TrainingCalendarView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		hourLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hourLabelPaint.setTextSize(50);
		hourLabelPaint.setColor(Color.BLACK);

		hourLineDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hourLineDividerPaint.setStrokeWidth(2);
		hourLineDividerPaint.setColor(Color.GRAY);

		refresh(null);
	}

	public void refresh(@Nullable Runnable onFinishedLoading) {
		new TrainingWeekFetcher(new IReceiver<TrainingWeek>() {
			@Override
			public void onReceive(@NonNull TrainingWeek obj) {
				setTrainingWeek(obj);
			}
		}, onFinishedLoading).execute();
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

		for(int hour = minHour; hour < maxHour + 1; hour++) {
			float hourY = getHourDrawY(hour);

			canvas.drawText(Integer.toString(hour), getPaddingLeft(), hourY, hourLabelPaint);
			canvas.drawLine(getPaddingLeft() + hourLabelPaint.getTextSize() * 1.5f, hourY - (0.5f * hourLabelPaint.getTextSize()),
					width - getPaddingRight(), hourY - (0.5f * hourLabelPaint.getTextSize()), hourLineDividerPaint);
		}

		for(int i = 0; i < 8; i++) {
			float x = i * trainingWidth + getPaddingLeft() + hourLabelPaint.getTextSize() * 2 - hourLineDividerPaint.getStrokeWidth();

			canvas.drawLine(x, hourLabelPaint.getTextSize() / 2f, x, height - (hourLabelPaint.getTextSize() / 2f), hourLineDividerPaint);
		}

		drawTrainings(canvas, getPaddingLeft() + hourLabelPaint.getTextSize() * 2f - 1, width - getPaddingRight(), hourLabelPaint.getTextSize() / 2f + 5, height - (hourLabelPaint.getTextSize() / 2f));
	}

	private void drawTrainings(Canvas canvas, float minX, float maxX, float minY, float maxY) {
		int dayIndex = 0;
		for(TrainingDay day : trainingWeek.getTrainingDays()) {
			int trainingIndex = 0;
			for(Training training : day.getTrainings()) {
				Log.v(MainActivity.LOG_TAG, String.format("Drawing from %s \n\tto %s", training.getStartTime().getTime().toString(), training.getEndTime().getTime().toString()));

				training.getDrawable().onDraw(canvas, minX + dayIndex * trainingWidth,
						getHourDrawY(DateUtil.getHours(training.getStartTime())) - hourLabelPaint.getTextSize() * 0.5f,
						trainingWidth,
						getTrainingHeight(training.getDurationInHours()));

				trainingIndex++;
			}

			dayIndex++;
		}
	}

	private void calculateDrawSizes() {
		if(trainingWeek == null)
			return;

		GregorianCalendar earliestTime = trainingWeek.getEarliestTime(), latestTime = trainingWeek.getLatestTime();

		Log.v(MainActivity.LOG_TAG, "Latest hour: " + latestTime.get(Calendar.HOUR_OF_DAY));

		minHour = earliestTime.get(Calendar.HOUR_OF_DAY);
		if(earliestTime.get(Calendar.MINUTE) > 0) {
			minHour++;
		}

		maxHour = latestTime.get(Calendar.HOUR_OF_DAY);
		if(latestTime.get(Calendar.MINUTE) > 0) {
			maxHour++;
		}

		int itemCount = (maxHour - minHour + 1);

		trainingHeight = (height - itemCount * hourLabelPaint.getTextSize()) / (itemCount - 1);
		trainingWidth = (width - getPaddingLeft() - hourLabelPaint.getTextSize() * 2) / trainingWeek.getTrainingDays().length;
	}

	private float getHourDrawY(float hour) {
		if(hour < minHour || hour > maxHour) {
			StringBuilder warn = new StringBuilder("Invalid hour: \n");
			for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
				warn.append("\t\t" + element.toString() + "\n");
			}
			Log.w(MainActivity.LOG_TAG, warn.toString());
			return -1;
		}

		float i = hour - minHour;
		float y = (i + 1) * hourLabelPaint.getTextSize() + i * trainingHeight;

		return y;
	}

	private float getTrainingHeight(float duration) {
		return (getHourDrawY(minHour + 1) - getHourDrawY(minHour)) * duration;
	}

	public TrainingWeek getTrainingWeek() {
		return trainingWeek;
	}

	public void setTrainingWeek(TrainingWeek trainingWeek) {
		this.trainingWeek = trainingWeek;
		calculateDrawSizes();

		invalidate();
	}
}
