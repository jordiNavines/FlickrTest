package com.jordi.navines.flickr.flickrtest.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by jordi on 21/09/2017.
 */

public class AppController extends Application {

    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;
    }


    public static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

}
