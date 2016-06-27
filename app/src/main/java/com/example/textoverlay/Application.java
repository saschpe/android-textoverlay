package com.example.textoverlay;

import saschpe.textoverlay.app.TextOverlayActivityLifecycleCallbacks;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Let's use the text overlay service only to display information on debug builds:
        if (BuildConfig.DEBUG) {
            registerActivityLifecycleCallbacks(new TextOverlayActivityLifecycleCallbacks());
        }
    }
}