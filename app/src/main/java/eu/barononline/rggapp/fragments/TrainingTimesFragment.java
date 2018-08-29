package eu.barononline.rggapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.barononline.rggapp.R;
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

		return root;
	}
}
