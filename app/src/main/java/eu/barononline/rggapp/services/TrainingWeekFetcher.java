package eu.barononline.rggapp.services;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.GregorianCalendar;
import java.util.Random;

import eu.barononline.rggapp.models.training.Training;
import eu.barononline.rggapp.models.training.TrainingDay;
import eu.barononline.rggapp.models.training.TrainingWeek;

public class TrainingWeekFetcher extends AsyncTask<String, String, TrainingWeek> {

	private IReceiver<TrainingWeek> receiver;
	private Runnable onTaskFinished;
	private Resources resources;

	public TrainingWeekFetcher(@NonNull IReceiver<TrainingWeek> receiver, Runnable onTaskFinished, Resources resources) {
		this.receiver = receiver;
		this.onTaskFinished = onTaskFinished;
		this.resources = resources;
	}

	@Override
	protected TrainingWeek doInBackground(String... strings) {
		TrainingDay[] times = new TrainingDay[7];
		for(int i = 0; i < times.length; i++) {
			times[i] = createTrainingTimes(i);
		}

		return new TrainingWeek(times, resources);
	}

	@Override
	protected void onPostExecute(TrainingWeek trainingWeek) {
		if(receiver == null)
			return;

		receiver.onReceive(trainingWeek);

		if(onTaskFinished != null)
			onTaskFinished.run();
	}

	private TrainingDay createTrainingTimes(int dow) {
		Training[] times = { createTraining() };

		return new TrainingDay(dow, times, resources);
	}

	private Training createTraining() {
		Random r = new Random();
		return new Training(
				new GregorianCalendar(2018, 8, 9, r.nextInt(10) + 5, (r.nextInt(3) + 1) * 15, 0),
				new GregorianCalendar(2018, 8, 9, r.nextInt(5) + 15, (r.nextInt(3) + 1) * 15, 0),
				"Lukas Mastaler",
				true,
				Training.TrainingType.Supervision,
				resources);
	}
}
