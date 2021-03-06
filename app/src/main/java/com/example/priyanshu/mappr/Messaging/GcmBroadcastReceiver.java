package com.example.priyanshu.mappr.Messaging;

/**
 * Created by priyanshu-sekhar on 7/3/15.
 */
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;

import com.example.priyanshu.mappr.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * See http://developer.android.com/google/gcm/client.html
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = "GcmBroadcastReceiver";
    private Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;

        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mWakeLock.acquire();

        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

            String messageType = gcm.getMessageType(intent);
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error", false);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server", false);

            } else {
                String msg = intent.getStringExtra(DataProvider.COL_MESSAGE);
                String email = intent.getStringExtra(DataProvider.COL_EMAIL);

                ContentValues values = new ContentValues(2);
                values.put(DataProvider.COL_MESSAGE, msg);
                values.put(DataProvider.COL_EMAIL, email);
                context.getContentResolver().insert(DataProvider.CONTENT_URI_MESSAGES, values);
                if (Common.isNotify()) {
                    sendNotification("New message", true);
                }
            }
            setResultCode(Activity.RESULT_OK);

        } finally {
            mWakeLock.release();
        }
        // Explicitly specify that GcmIntentService will handle the intent.
        /*ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);*/
    }


    private void sendNotification(String text, boolean launchApp) {
        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder mBuilder = new Notification.Builder(ctx)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(ctx.getString(R.string.app_name))
                .setContentText(text);

        if (!TextUtils.isEmpty(Common.getRingtone())) {
            mBuilder.setSound(Uri.parse(Common.getRingtone()));
        }

        if (launchApp) {
            Intent intent = new Intent(ctx, Messaging.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pi);
        }

        mNotificationManager.notify(1, mBuilder.getNotification());
    }



}