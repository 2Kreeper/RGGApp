package eu.barononline.rggapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import eu.barononline.rggapp.models.Training;

public class TrainingDetailActivity extends BaseActivity {

	public static final String TRAINING_KEY = TrainingDetailActivity.class.getName() + ".TRAINING";

	private Training training;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_training_detail);
		super.onCreate(savedInstanceState);

		try {
			training = new Training(new JSONObject(getIntent().getStringExtra(TRAINING_KEY)));
		} catch (JSONException e) {
			e.printStackTrace();
			finish();
		}
	}
}
