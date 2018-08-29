package eu.barononline.rggapp.fragments.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.events.Event;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListViewHolder> {

    private Event[] events;

    public EventsListAdapter(Event[] events) {
        this.events = events;
    }

    @Override
    public EventsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_events, parent, false);

        return new EventsListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(EventsListViewHolder holder, int position) {
        Event event = events[position];

        holder.titleTextView.setText(event.title);
        holder.locationTextView.setText(event.location);
        holder.timeTextView.setText(event.time);
    }

    @Override
    public int getItemCount() {
        return events.length;
    }
}
