package com.example.nabha.slideshow.model;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by nabha on 04/08/17.
 */

public class GalleryApplication extends Application {

    private static GalleryApplication mGalleryApplication;

    public static GalleryApplication getInstance() {
        return mGalleryApplication;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGalleryApplication = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }



}
