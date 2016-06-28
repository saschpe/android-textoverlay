package com.example.textoverlay.one;

import saschpe.textoverlay.app.TextOverlayActivityLifecycleCallbacks;
import saschpe.textoverlay.service.TextOverlayService;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Set default text initially
        String overlayText = BuildConfig.APPLICATION_ID + " (" + BuildConfig.VERSION_NAME + " - "
                + BuildConfig.BUILD_TYPE + ")";
        TextOverlayService.setText(this, overlayText);

        // Register lifecycle callbacks and let them do the heavy lifting
        registerActivityLifecycleCallbacks(new TextOverlayActivityLifecycleCallbacks());
    }
}