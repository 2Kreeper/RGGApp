package eu.barononline.rggapp.services;

import android.support.annotation.NonNull;

public interface IReceiver<T> {

	void onReceive(@NonNull T obj);
}
