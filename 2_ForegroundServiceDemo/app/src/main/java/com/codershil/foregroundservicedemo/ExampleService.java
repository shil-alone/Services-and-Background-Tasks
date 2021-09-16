package com.codershil.foregroundservicedemo;

import static com.codershil.foregroundservicedemo.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ExampleService extends Service {

    @Override
    public void onCreate() {
        // we can also set notification here . but we need intent that's why we created it inside onStartCommand
        super.onCreate();
    }

    // this method is called when we have a bound service . that means our service interacting with other components
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // it runs on ui thread on defaul . we should create threads
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // here we will get the data from intent that we have sent from mainactivity
        String input = intent.getStringExtra("inputExtra");

        // intent for when we click on notification
        Intent notificationIntent = new Intent(this,MainActivity.class);
        // it is the intent to set the onclick on notification
        // request code is used to cancel this pending intent,and flag defines what happens when we update this pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);


        // in lower than api 26 oreo the channel id will be ignored and our app will not crash
        Notification notification =new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        // do heavy work here

        // this shows the notification it requires id more than 0
        // this will start the service in foreground . if we don't call it then it will start in background and will be killed after 1 minute
        startForeground(1,notification);

        //we can stopSelf to stop this service
//        stopSelf();

        // here we can to return one of three values
        // START_NOT_STICKY . if we return this the service will not start again after killed
        // START_STICKY . if we return this the service will again start again after killed but with null intent
        // START_REDELIVER_INTENT if we return this the service will again start again after killed but with previous intent

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
