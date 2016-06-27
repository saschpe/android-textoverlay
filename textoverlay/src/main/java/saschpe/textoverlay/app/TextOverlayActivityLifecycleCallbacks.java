package saschpe.textoverlay.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import saschpe.textoverlay.service.TextOverlayService;

public class TextOverlayActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activity.startService(new Intent(activity, TextOverlayService.class));
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activity.stopService(new Intent(activity, TextOverlayService.class));
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
