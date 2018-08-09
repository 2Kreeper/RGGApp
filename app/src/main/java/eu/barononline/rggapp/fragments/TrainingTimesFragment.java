package eu.barononline.rggapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;
import eu.barononline.rggapp.TrainingDetailActivity;
import eu.barononline.rggapp.models.Training;
import eu.barononline.rggapp.models.TrainingWeek;
import eu.barononline.rggapp.services.IReceiver;
import eu.barononline.rggapp.services.TrainingWeekFetcher;
import eu.barononline.rggapp.views.ITrainingTouchListener;
import eu.barononline.rggapp.views.TrainingCalendarView;

public class TrainingTimesFragment extends Fragment {

	private TrainingCalendarView calendarView;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_training_times, container, false);

		calendarView = root.findViewById(R.id.trainingCalendarView);
		swipeRefreshLayout = root.findViewById(R.id.swipe_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				calendarView.refresh(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
					}
				});
			}
		});

		calendarView.addTrainingTouchListener(new ITrainingTouchListener() {
			@Override
			public void onTrainingTouch(Training training, MotionEvent event) {
				Intent intent = new Intent(getContext(), TrainingDetailActivity.class);
				intent.putExtra(TrainingDetailActivity.TRAINING_KEY, training.toString());
				startActivity(intent);
			}
		});

		return root;
	}
}
