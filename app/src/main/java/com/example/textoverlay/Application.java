package com.example.textoverlay;

import saschpe.textoverlay.app.TextOverlayActivityLifecycleCallbacks;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new TextOverlayActivityLifecycleCallbacks());
    }
}