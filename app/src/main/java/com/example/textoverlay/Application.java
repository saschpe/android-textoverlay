package com.example.textoverlay;

import saschpe.textoverlay.app.TextOverlayActivityLifecycleCallbacks;
import saschpe.textoverlay.service.TextOverlayService;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String overlayText = BuildConfig.APPLICATION_ID + " (" + BuildConfig.VERSION_NAME + " - "
                + BuildConfig.BUILD_TYPE + ")";
        TextOverlayService.setText(this, overlayText);

        registerActivityLifecycleCallbacks(new TextOverlayActivityLifecycleCallbacks());
    }
}