package com.codershil.intentservicedemo;

import static com.codershil.intentservicedemo.App.CHANNEL_ID;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


// this class do the same work as a foreground service but it runs on the background thread by default. but we can use only one thread at time.
// all the jobs are executed sequentially one after another .
// service gets automatically killed after the execution is completed

public class ExampleIntentService extends IntentService {

    public static final String TAG = "ExampleIntentService";

    // object for wakelock management
    private PowerManager.WakeLock wakeLock;

    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(false); // if we pass false then it will not be restarted , if we pass true it will be restarted after interruption and
        // last intent will be delivered to the service
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        // here we are acquiring wake lock so that when the screen is off our service can use cpu
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"ExampleApp:WakeLock");
        wakeLock.acquire(); // here we should provide timeout for wakelock otherwise it will drain the battery

        // to show notification we use this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle("Example Intent Service")
                    .setContentText("Running ...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();
                    
            startForeground(1,notification);

        }

    }

    // this is called when this service gets an intent
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        String input = intent.getStringExtra("inputExtra");
        for (int i = 0; i <10; i++) {
            SystemClock.sleep(1000);
            Log.d(TAG, "onHandleIntent: "+i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        wakeLock.release(); // this method is to stop the wakelock .to prevent battery drain

        Log.d(TAG, "onDestroy: wakelock released");
    }
}
