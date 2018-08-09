package eu.barononline.rggapp.views;

import android.view.MotionEvent;

import eu.barononline.rggapp.models.Training;

public interface ITrainingTouchListener {

	public void onTrainingTouch(Training training, MotionEvent event);
}
