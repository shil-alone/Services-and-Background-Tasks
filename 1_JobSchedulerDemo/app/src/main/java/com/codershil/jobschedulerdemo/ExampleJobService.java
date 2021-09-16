package com.codershil.jobschedulerdemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

// it is a class for executing our job
public class ExampleJobService extends JobService {

    public static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;


    // this method is called when we schedule and start our job
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "job started");
        doBackgroundWork(params);
        
        // it returns false to tell that our job is finished
        // if we return true it says that keep our device awake until our job is not finished . that means it is our responsibility to
        // tell the android to stop the service
        return true;
    }

    public void doBackgroundWork(JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10; i++) {
                    // if job cancelled is true this this job will stop executing
                    if (jobCancelled){
                        return;
                    }
                    Log.d(TAG, "run : "+i);
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException interruptedException){
                        interruptedException.printStackTrace();
                    }
                }
                Log.d(TAG, "run: job finished");
                // this method tells that our job is finished and we pass params , and if we want to reschedule this job or not
                jobFinished(params,false);
            }
        }).start();
    }

    // this method is called when we cancel the job
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job cancelled before completion");
        jobCancelled = true;
        // if we return false that means we don't want to reschedule this job again
        // if we true then we will reschedule it again
        return true;
    }
}
