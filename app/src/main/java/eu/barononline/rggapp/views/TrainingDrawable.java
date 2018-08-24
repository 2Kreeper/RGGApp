package eu.barononline.rggapp.views;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;

import eu.barononline.rggapp.models.training.Training;

public class TrainingDrawable implements IDrawable {

	private Training training;
	private Resources resources;
	private Resources.Theme theme;

	private float x, y, width, height;

	public TrainingDrawable(Training training, Resources resources) {
		this.training = training;
		this.resources = resources;
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	public TrainingDrawable(Training training, Resources resources, Resources.Theme theme) {
		this(training, resources);
		this.theme = theme;
	}

	@Override
	public void onDraw(Canvas canvas, float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && theme != null) {
			canvas.drawRect(x, y, x + width, y + height, training.getType().getPaint(resources, theme));
		} else {
			canvas.drawRect(x, y, x + width, y + height, training.getType().getPaint(resources));
		}
	}

	public boolean contains(float xTouch, float yTouch) {
		return xTouch > x && xTouch < x + width &&
				yTouch > y && yTouch < y + height;
	}
}
