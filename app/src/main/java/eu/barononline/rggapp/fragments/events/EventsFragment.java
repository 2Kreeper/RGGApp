package eu.barononline.rggapp.fragments.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.events.Event;
import eu.barononline.rggapp.services.EventListFetcher;
import eu.barononline.rggapp.services.IReceiver;

public class EventsFragment extends Fragment implements IReceiver<Event[]> {

	private RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_events, container, false);

		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView = root.findViewById(R.id.event_list);
		recyclerView.setLayoutManager(llm);
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

		new EventListFetcher(this).execute();

		return root;
	}


	@Override
	public void onReceive(Event[] obj) {
		if(obj == null)
			return;

		//Log.v(MainActivity.LOG_TAG, "Received object array!");
		recyclerView.setAdapter(new EventsListAdapter(obj));
		recyclerView.invalidate();
	}
}
