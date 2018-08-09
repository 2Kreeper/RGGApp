package eu.barononline.rggapp.services;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.models.Training;
import eu.barononline.rggapp.models.TrainingDay;
import eu.barononline.rggapp.models.TrainingWeek;

public class TrainingWeekFetcher extends AsyncTask<String, String, TrainingWeek> {

	private IReceiver<TrainingWeek> receiver;
	private Runnable onTaskFinished;

	public TrainingWeekFetcher(@NonNull IReceiver<TrainingWeek> receiver, Runnable onTaskFinished) {
		this.receiver = receiver;
		this.onTaskFinished = onTaskFinished;
	}

	@Override
	protected TrainingWeek doInBackground(String... strings) {
		TrainingDay[] times = new TrainingDay[7];
		for(int i = 0; i < times.length; i++) {
			times[i] = createTrainingTimes(i);
		}

		return new TrainingWeek(times);
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

		return new TrainingDay(dow, times);
	}

	private Training createTraining() {
		Random r = new Random();
		return new Training(
				new GregorianCalendar(2018, 8, 9, r.nextInt(10) + 5, 0, 0),
				new GregorianCalendar(2018, 8, 9, r.nextInt(5) + 15, 0, 0),
				"Lukas Mastaler",
				true,
				Training.TrainingType.Regatta);
	}
}
