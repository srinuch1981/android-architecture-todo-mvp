package com.example.android.architecture.blueprints.todoapp;

import static android.os.Build.VERSION_CODES.GINGERBREAD;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by schava on 10/31/2016.
 */

public class ToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        enabledStrictMode();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void enabledStrictMode() {
        if (Build.VERSION.SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }
}
