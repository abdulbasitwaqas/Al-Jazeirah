package com.app.aljazierah.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.aljazierah.R;
import com.app.aljazierah.ui.HomeScreen.HomeScreenActivity;
import com.app.aljazierah.ui.OrderDetails;
import com.app.aljazierah.ui.SplashScreenActivity;
//import com.clevertap.android.sdk.CleverTapAPI;
//import com.clevertap.android.sdk.NotificationInfo;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "**token";
    //this method will receive message from the firebase which we will have to customize
    String reference_id = "";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //the message we receive from server we will show in the console with log
        Log.d(TAG,"fromfirebase:"+remoteMessage.getData());
        //Log.d(TAG,"Notification Message Body:"+remoteMessage.getNotification().getBody());
//        if (remoteMessage.getData().size() > 0) {
            Bundle extras = new Bundle();
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                extras.putString(entry.getKey(), entry.getValue());
                Log.d("keyvalueeee", entry.getKey()+":"+entry.getValue());
                if (entry.getKey().equals("reference_id")){reference_id=entry.getValue();}
            }
            NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
            if (info.fromCleverTap) {
                CleverTapAPI.createNotification(getApplicationContext(), extras);
            } else {
                // not from CleverTap handle yourself or pass to another provider
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendNotificationForOreoAndAbove(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),reference_id);
                }
                else{
                    sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),reference_id);
                }
            }
//        }
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKEN",s);
        // sendRegistrationToServer(token);
    }



    private void sendNotification(String title, String body,String reference_id){

        // is service sa message hum orderdetail activity ma la ja rae han
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        @SuppressLint("WrongConstant")
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setBadgeIconType(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultsoundUri)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotificationForOreoAndAbove(String title, String body, String reference_id) {


        NotificationChannel channel = new NotificationChannel("channel01", "name",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("description");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Intent intent;

        if (!reference_id.equals("")) {
            intent = new Intent(this, OrderDetails.class);
            intent.putExtra("order_id", reference_id);
        } else {
            intent = new Intent(this, HomeScreenActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);

        @SuppressLint("WrongConstant")
        Notification notification = new NotificationCompat.Builder(this, "channel01")
                .setSmallIcon(R.drawable.app_icon)
                .setBadgeIconType(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(this);
        notificationManager1.notify(0, notification);

    }
}