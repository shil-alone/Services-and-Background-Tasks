package com.codershil.jobschedulerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int jobId = 1;
    private static final String TAG = "ExampleJobService";
    Button btnStartJob, btnCancelJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartJob = findViewById(R.id.startJob);
        btnCancelJob = findViewById(R.id.cancelJob);
        btnStartJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });
        btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJob();
            }
        });
    }

    // this method is for scheduling job
    private void scheduleJob(){
        // this is a component identifier for our service
        ComponentName componentName = new ComponentName(this,ExampleJobService.class);

        // it is an object which specifies when our job should be started
        JobInfo jobInfo = new JobInfo.Builder(jobId,componentName)
                .setRequiresCharging(true) // that means this job will only run if we are charging the device
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // it will run if any type of network is connected . we can put more things here
                .setPersisted(true)         // our service will keep running also if device is rebooted
                .setPeriodic(15*60*1000)    // it will run our service after every 15 minute . if is minimum required from android oreo
                .build();

        // it is the actual object responsible to schedule our job
        JobScheduler jobScheduler =(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "job scheduled successfully");
        }
        else{
            Log.d(TAG, "job scheduling failed");
        }

    }

    private void cancelJob(){
        JobScheduler jobScheduler =(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
        Log.d(TAG, "job cancelled");
    }
}