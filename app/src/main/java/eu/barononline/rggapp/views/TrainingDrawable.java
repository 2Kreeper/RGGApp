package eu.barononline.rggapp.views;

import android.graphics.Canvas;

import eu.barononline.rggapp.models.Training;

public class TrainingDrawable implements IDrawable {

	private Training training;

	private float x, y, width, height;

	public TrainingDrawable(Training training) {
		this.training = training;
	}

	@Override
	public void onDraw(Canvas canvas, float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		canvas.drawRect(x, y, x + width, y + height, training.getType().getPaint());
	}

	public boolean contains(float xTouch, float yTouch) {
		return xTouch > x && xTouch < x + width &&
				yTouch > y && yTouch < y + height;
	}
}
