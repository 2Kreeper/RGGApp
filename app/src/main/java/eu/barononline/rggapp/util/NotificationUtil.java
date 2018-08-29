package eu.barononline.rggapp.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.lang.reflect.Array;

public class NotificationUtil {

	public enum Channel {
		@RequiresApi(api = Build.VERSION_CODES.N)
		Updates(NotificationUtil.class.getPackage().getName() + ".Updates", 0, 0, NotificationManager.IMPORTANCE_DEFAULT);

		private String id;
		private int nameRes, descriptionRes, importance;

		Channel(String id, int nameRes, int descriptionRes, int importance) {
			this.id = id;
			this.nameRes = nameRes;
			this.descriptionRes = descriptionRes;
			this.importance = importance;
		}

		public String getChannelId() {
			return id;
		}

		public int getNameRes() {
			return nameRes;
		}

		public int getDescriptionRes() {
			return descriptionRes;
		}

		public int getImportance() {
			return importance;
		}
	}

	private static boolean registered = false;
	private static void registerChannels(Context context) {
		if(registered)
			return;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			Resources resources = context.getResources();

			for(Channel channel : Channel.values()) {
				NotificationChannel notificationChannel = new NotificationChannel(channel.getChannelId(),
						resources.getString(channel.getNameRes()), channel.getImportance());
				notificationChannel.setDescription(resources.getString(channel.getDescriptionRes()));

				context.getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

				//Settings.Secure.ANDROID_ID
			}
		}

		registered = true;
	}

	public static void showNotification(Context context, int smallIcon, int priority, String title, String body, PendingIntent onTap, Integer id, long[] vibrationPattern) {
		showNotification(context, null, smallIcon, priority, title, body, onTap, id, vibrationPattern);
	}


	private static int maxId = 0;
	public static void showNotification(Context context, Channel channel, int smallIcon, int priority, String title, String body, PendingIntent onTap, Integer id, long[] vibrationPattern) {
		registerChannels(context);

		String channelName = "";
		if(channel != null)
			channelName = channel.name();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelName)
				.setSmallIcon(smallIcon)
				.setPriority(priority)
				.setVibrate(vibrationPattern);

		if(title != null)
			builder.setContentTitle(title);
		if(body != null)
			builder.setContentText(body);
		if(onTap != null)
			builder.setContentIntent(onTap);

		Notification notification = builder.build();
		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

		if(id != null && id > maxId) {
			maxId = id;
		} else {
			maxId++;
		}

		notificationManager.notify(maxId, notification);
	}
}
