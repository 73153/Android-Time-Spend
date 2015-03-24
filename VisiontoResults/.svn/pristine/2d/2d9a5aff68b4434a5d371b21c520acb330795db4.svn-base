package com.ijoomer.tips;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tasol on 19/12/14.
 */
public class NotificationBroadCastReceiver extends BroadcastReceiver {

    private NotificationAlarmReceiver alarmReceiver = new NotificationAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarmReceiver.setAlarm(context);
        }
    }
}
