package com.example.ex1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class CustomApplicationClass extends Application {
    private static Context appContext;
    public static final String MESSAGECOUNT = "MESSAGE_COUNT";
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        LogNumOfMessages();

    }
    public static Context getAppContext() {
        return appContext;
    }

    public static void LogNumOfMessages() {
        Context context = CustomApplicationClass.getAppContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Log.i("MESSAGE_COUNT", Integer.toString(prefs.getInt(MESSAGECOUNT,0)));
    }
}
