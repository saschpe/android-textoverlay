package saschpe.textoverlay.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Service to display an overlay of arbitrary text.
 *
 * Useful for debugging purposes, because all QA / beta tester screenshots will include
 * build type, product flavor and additional information provided by you.
 *
 * If the app targets API level 23 or higher, the app user must explicitly grant this permission to
 * the app through a permission management screen. You can either do this yourself or use the
 * {@link saschpe.textoverlay.app.TextOverlayActivityLifecycleCallbacks} for convenience.
 *
 * @see <a href="https://developer.android.com/reference/android/Manifest.permission.html#SYSTEM_ALERT_WINDOW">SYSTEM_ALERT_WINDOW</a>
 * @see <a href="https://developer.android.com/reference/android/provider/Settings.html#ACTION_MANAGE_OVERLAY_PERMISSION">ACTION_MANAGE_OVERLAY_PERMISSION</a>
 */
public final class TextOverlayService extends Service {
    private static final String TAG = TextOverlayService.class.getSimpleName();
    private static String lastUsedOverlayText;

    public static final String ACTION_SET_TEXT = "saschpe.textoverlay.service.SET_TEXT";
    public static final String EXTRA_TEXT = "saschpe.textoverlay.service.text";

    private TextView textView;

    /**
     * Helper method that simplifies updating overlay text.
     *
     * @param context A context.
     * @param text The new overlay text.
     */
    public static void setText(final Context context, final String text) {
        Intent intent = new Intent(TextOverlayService.ACTION_SET_TEXT);
        intent.putExtra(TextOverlayService.EXTRA_TEXT, text);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        lastUsedOverlayText = text;
    }

    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTextFromIntent(intent);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        getTextFromIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: Starting service");
        super.onCreate();

        int colorId = getResources().getIdentifier("red", "color", getPackageName());

        textView = new TextView(this);
        textView.setTextColor(ContextCompat.getColor(this, colorId));
        textView.setText(lastUsedOverlayText);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        params.setTitle("Text Overlay");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(textView, params);

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(ACTION_SET_TEXT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);

        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(textView);
        textView = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getTextFromIntent(Intent intent) {
        // Get extra data included in the Intent
        final String message = intent.getStringExtra(EXTRA_TEXT);
        if (message != null) {
            Log.d(TAG, "getTextFromIntent() Got message: " + message);
            textView.setText(message);
        }
    }
}
