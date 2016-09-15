package com.cclouds.mysmsgateway;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by anooj on 14/09/16.
 */
public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        AndroidNetworking.initialize(getApplicationContext());
    }

    public static Context getAppContext() {
        return App.context;
    }
}
