package com.app.aljazierah.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.aljazierah.R;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;


public class MyHMSMessagingService extends HmsMessageService {
    private static final String TAG = "Berain App Token";
    //this method will receive message from the firebase which we will have to customize
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //the message we receive from server we will show in the console with log
        Log.e(TAG,"from:"+remoteMessage);
        Log.e(TAG,"Notification Message Body:"+remoteMessage.getNotification().getBody());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotificationForOreoAndAbove(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        else{
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }
        @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN",s);
       // sendRegistrationToServer(token);
    }
    private void sendNotification(String title, String body){
        // is service sa message hum orderdetail activity ma la ja rae han
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultsoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());
    }


    private void sendNotificationForOreoAndAbove(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(0 + "", body, NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.

            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.app_icon);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, 0 + "")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.app_icon)

                .setContentTitle(title)
                .setContentText(body)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setAutoCancel(true)

                .setContentIntent(pendingIntent);


        notificationManager.notify(0, builder.build());


    }


}
