package eu.barononline.rggapp.fcm;

import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import eu.barononline.rggapp.MainActivity;
import eu.barononline.rggapp.R;
import eu.barononline.rggapp.util.NotificationUtil;

public class FCMReciever extends FirebaseMessagingService {

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		Log.d(MainActivity.LOG_TAG, String.format("Received message from \"%s\":\n\n%s\n%s", remoteMessage.getFrom(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			NotificationUtil.showNotification(
					getApplicationContext(),
					NotificationUtil.Channel.Updates,
					R.drawable.ic_notification_data_update,
					0,
					remoteMessage.getNotification().getTitle(),
					remoteMessage.getNotification().getBody(),
					null,
					null,
					new long[] {100, 50, 100}
			);
		} else {
			NotificationUtil.showNotification(
					getApplicationContext(),
					R.drawable.ic_notification_data_update,
					0,
					remoteMessage.getNotification().getTitle(),
					remoteMessage.getNotification().getBody(),
					null,
					null,
					new long[] {100, 50, 100}
			);
		}
	}
}
