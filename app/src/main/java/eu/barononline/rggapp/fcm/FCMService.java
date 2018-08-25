package eu.barononline.rggapp.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import eu.barononline.rggapp.MainActivity;

public class FCMService extends FirebaseInstanceIdService {

	@Override
	public void onTokenRefresh() {
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		Log.v(MainActivity.LOG_TAG, "FCM token: " + refreshedToken);

		//sendRegistratio nToServer(refreshedToken);
	}
}
