//package com.example.ex1;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import java.util.UUID;
//
//public class CustomApplicationClass extends Application {
//    private static Context appContext;
//    public static final String MESSAGECOUNT = "MESSAGE_COUNT";
//    public static final String USER = "USER";
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        appContext = getApplicationContext();
//        Context context = CustomApplicationClass.getAppContext();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        LogNumOfMessages(prefs);
//        if(!prefs.contains(USER)) {
//            createUserUUID(prefs);
//        }
//
//    }
//
//    private void createUserUUID(SharedPreferences prefs) {
//        String uniqueID = UUID.randomUUID().toString();
//        prefs.edit().putString(USER , uniqueID).apply();
//    }
//
//    public static Context getAppContext() {
//        return appContext;
//    }
//
//    public static void LogNumOfMessages(SharedPreferences prefs) {
//        Log.i("MESSAGE_COUNT", Integer.toString(prefs.getInt(MESSAGECOUNT,0)));
//    }
//}
