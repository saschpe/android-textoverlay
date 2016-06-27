package saschpe.textoverlay.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import saschpe.textoverlay.service.TextOverlayService;

/**
 * Activity lifecycle callbacks that handle {@link TextOverlayService} interactions.
 *
 * Handles start / stop of the service as well as asking for the required permissions on
 * API level 23 or later.
 *
 * @see <a href="https://developer.android.com/reference/android/Manifest.permission.html#SYSTEM_ALERT_WINDOW">SYSTEM_ALERT_WINDOW</a>
 * @see <a href="https://developer.android.com/reference/android/provider/Settings.html#ACTION_MANAGE_OVERLAY_PERMISSION">ACTION_MANAGE_OVERLAY_PERMISSION</a>
 */
public final class TextOverlayActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = TextOverlayActivityLifecycleCallbacks.class.getSimpleName();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(activity)) {
            Log.d(TAG, "onActivityCreated: API level 23 and not can draw overlays");
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, 1234);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed");
        if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(activity)) {
            Log.d(TAG, "onActivityResumed: API level 23 and can draw overlays");
            activity.startService(new Intent(activity, TextOverlayService.class));
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused");
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
