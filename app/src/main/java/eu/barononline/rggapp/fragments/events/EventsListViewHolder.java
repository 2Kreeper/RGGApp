package eu.barononline.rggapp.fragments.events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import eu.barononline.rggapp.R;
import eu.barononline.rggapp.models.events.Event;

public class EventsListViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public TextView titleTextView, locationTextView, timeTextView;

    public EventsListViewHolder(View itemView) {
        super(itemView);

        this.view = itemView;

        titleTextView = itemView.findViewById(R.id.item_title);
        locationTextView = itemView.findViewById(R.id.item_location);
        timeTextView = itemView.findViewById(R.id.item_time);
    }
}
