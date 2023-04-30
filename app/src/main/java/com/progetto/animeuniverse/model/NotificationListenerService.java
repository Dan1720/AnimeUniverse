package com.progetto.animeuniverse.model;


import android.app.Notification;
import android.content.Intent;
import android.service.notification.StatusBarNotification;

import com.progetto.animeuniverse.util.Constants;

public class NotificationListenerService extends android.service.notification.NotificationListenerService{

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Notification notification = sbn.getNotification();
        if (notification != null) {
            Intent intent = new Intent(Constants.ACTION_NOTIFICATION_RECEIVED);
            intent.putExtra(Notification.EXTRA_TITLE, notification.extras.getString(Notification.EXTRA_TITLE));
            intent.putExtra(Notification.EXTRA_TEXT, notification.extras.getString(Notification.EXTRA_TEXT));
            sendBroadcast(intent);
        }
    }
}
