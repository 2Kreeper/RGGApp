package eu.barononline.rggapp.services;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.GregorianCalendar;
import java.util.Random;

import eu.barononline.rggapp.models.Trainer;
import eu.barononline.rggapp.models.training.Training;
import eu.barononline.rggapp.models.training.TrainingDay;
import eu.barononline.rggapp.models.training.TrainingWeek;
import eu.barononline.rggapp.util.NetworkUtil;

public class TrainingsFetcher extends AsyncTask<String, String, Training[]> {

	private IReceiver<Training[]> receiver;
	private Runnable onTaskFinished;
	private Resources resources;

	public TrainingsFetcher(@NonNull IReceiver<Training[]> receiver, Runnable onTaskFinished, Resources resources) {
		this.receiver = receiver;
		this.onTaskFinished = onTaskFinished;
		this.resources = resources;
	}

	@Override
	protected Training[] doInBackground(String... strings) {
		Training[] trainings = null;

		/*trainings = new Training[] {
				new Training(
						new GregorianCalendar(1, 1, 1, 14, 0, 0), new GregorianCalendar(1, 1, 1, 18, 0, 0),
						new Trainer("Lukas", "Mastaler"), true, Training.TrainingType.Supervision, 0, resources
				),
				new Training(
						new GregorianCalendar(1, 1, 1, 14, 0, 0), new GregorianCalendar(1, 1, 1, 16, 0, 0),
						new Trainer("Lukas", "Mastaler"), true, Training.TrainingType.Regatta, 1, resources
				)
		};*/

		try {
			JSONArray result = new JSONArray(NetworkUtil.sendRequestGET(NetworkUtil.BASE_URL + "trainings", "deep=true"));
			trainings = new Training[result.length()];

			for(int i = 0; i < trainings.length; i++) {
				trainings[i] = new Training(result.getJSONObject(i), resources);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return trainings;
	}

	@Override
	protected void onPostExecute(Training[] trainingWeek) {
		if(receiver == null)
			return;

		receiver.onReceive(trainingWeek);

		if(onTaskFinished != null)
			onTaskFinished.run();
	}
}
