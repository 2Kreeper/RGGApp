package eu.barononline.rggapp.fragments.trainingtimes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.training.Training;
import eu.barononline.rggapp.util.DateUtil;
import eu.barononline.rggapp.views.ITrainingTouchListener;
import eu.barononline.rggapp.views.TrainingCalendarView;

public class TrainingTimesFragment extends Fragment {

	private TrainingCalendarView calendarView;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
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
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				View dialogView = inflater.inflate(R.layout.dialog_training_detail, null);

				TextView from = dialogView.findViewById(R.id.from_display), to = dialogView.findViewById(R.id.to_display),
						trainer = dialogView.findViewById(R.id.trainer_display), type = dialogView.findViewById(R.id.training_type_display);

				from.setText(DateUtil.toTimeString(training.getStartTime(), getResources()));
				to.setText(DateUtil.toTimeString(training.getEndTime(), getResources()));
				trainer.setText(training.getTrainer().toString());
				type.setText(training.getType().toLocalizedString(getResources()));

				builder.setView(dialogView)
						.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});

				builder.create().show();
			}
		});

		return root;
	}
}
