package com.ijoomer.intafy.src;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * This Class Contains All Method Related To GCMIntentService.
 *
 * @author tasol
 *
 */
public class GcmIntentService extends IntentService {

    private static int count = 1;
    private String PUSHTABLE="pushTable";
    private boolean isAddToStatusBar;

    public GcmIntentService() {
        super(IjoomerApplicationConfiguration.getGCMProjectId());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleMessage(this, intent);
    }

    /**
     * Class methods
     */

    /**
     * This methods used to handle push notification message.
     *
     * @param mContext
     *            represented {@link Context}
     * @param intent
     *            represented {@link Intent}
     */
    @SuppressWarnings("deprecation")
    private void handleMessage(Context mContext, Intent intent) {
        long when = System.currentTimeMillis();
        int icon = R.drawable.ijoomer_push_notification_icon;

        try {
            isAddToStatusBar = false;
            Bundle gcmData = intent.getExtras();

            NotificationManager notificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent contentIntent = null;
            if (gcmData.getString("type").equals("backend")) {
                contentIntent = PendingIntent.getActivity(mContext,
                        (int) (Math.random() * 100), new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                isAddToStatusBar =true;
            } else {
                Intent gotoIntent = new Intent();
                gotoIntent
                        .setClassName(mContext,
                                "com.ijoomer.intafy.src.IjoomerPushNotificationLuncherActivity");
                gotoIntent.putExtra("IN_PUSH_TYPE",
                        gcmData.getString("type"));
                gotoIntent.putExtra("IN_PUSH_ID", gcmData.getString("id"));
                contentIntent = PendingIntent.getActivity(mContext,
                        (int) (Math.random() * 100), gotoIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                JSONObject cachePushData = new JSONObject();
                cachePushData.put("networkId",gcmData.getString("id").split(":")[0]);
                cachePushData.put("connectedUserId",gcmData.getString("id").split(":")[1]);
                cachePushData.put("pushId",gcmData.getString("id").split(":")[2]);
                cachePushData.put("type",gcmData.getString("type"));
                new IjoomerCaching(this).cacheData(cachePushData, false, PUSHTABLE);
                HashMap<String,String> network = new IntafyNetworkDataProvider(this).getNetwork(cachePushData.getString("networkId"), cachePushData.getString("connectedUserId"));
                if(network!=null && network.size()>0){
                    isAddToStatusBar =true;
                }
                try {
                    if (IjoomerUtilities.mSmartAndroidActivity instanceof IjoomerHomeActivity) {
                        ((IjoomerHomeActivity)IjoomerUtilities.mSmartAndroidActivity).updateNotifiaction();
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            if(isAddToStatusBar) {
                Notification notification = new Notification(icon,
                        gcmData.getString("message"), when);
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                notification.setLatestEventInfo(mContext,
                        gcmData.getString("type"),
                        intent.getExtras().getString("message"), contentIntent);
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(count, notification);
                count = count + 1;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
