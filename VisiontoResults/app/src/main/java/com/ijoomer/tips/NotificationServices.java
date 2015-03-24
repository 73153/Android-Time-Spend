package com.ijoomer.tips;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by tasol on 19/12/14.
 */
public class NotificationServices extends IntentService {

    private static int count = 1;
    private NotificationManager mNotificationManager;

    public NotificationServices() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }


    private void sendNotification() {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, InspireMeActivity.class), 0);

        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.push_notification_icon)
        .setContentTitle(getString(R.string.app_name))
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(getString(R.string.push_message)))
        .setContentText(getString(R.string.push_message))
        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(count, mBuilder.build());
        count++;
    }
}

