package com.example.project_test.model.utils.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

public class OreoNotification extends ContextWrapper {
    public final static String CHANNEL_ID="com.example.project_test";
    public final static String CHANNEL_NAME="TGAS";
       private NotificationManager notificationManager;
    public OreoNotification(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createNotification();
        }
    }
   @TargetApi(Build.VERSION_CODES.O)
    private void createNotification() {
       NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
       notificationChannel.enableLights(true);
       notificationChannel.enableVibration(true);
       notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
      getManager().createNotificationChannel(notificationChannel);
    }
    public  NotificationManager getManager(){
        if(notificationManager==null){
            notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotification(String body, String title, PendingIntent pendingIntent, Uri sound, String icon){
        return  new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(Integer.parseInt(icon))
                .setSound(sound)
                .setAutoCancel(true);

    }
}
