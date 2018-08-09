package eu.barononline.rggapp.views;

import android.graphics.Canvas;

import eu.barononline.rggapp.models.Training;

public class TrainingDrawable implements IDrawable {

	private Training training;

	public TrainingDrawable(Training training) {
		this.training = training;
	}

	@Override
	public void onDraw(Canvas canvas, float x, float y, float width, float height) {
		canvas.drawRect(x, y, x + width, y + height, training.getType().getPaint());
	}
}
