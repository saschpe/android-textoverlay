package saschpe.textoverlay.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import saschpe.textoverlay.BuildConfig;

/**
 * Service to display an overlay of arbitrary text.
 */
public class TextOverlayService extends Service {
    private static final String TAG = TextOverlayService.class.getSimpleName();

    public static final String EVENT_SET_OVERLAY_TEXT = "saschpe.textoverlay.service.SET_OVERLAY_TEXT";
    public static final String ACTION_OVERLAY_TEXT = "saschpe.textoverlay.service.text";

    private TextView textView;

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            final String message = intent.getStringExtra(ACTION_OVERLAY_TEXT);
            if (message != null) {
                Log.d(TAG, "onReceive() Got message: " + message);
                updateText(message);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        textView = new TextView(this);
        updateText("");

        // Set default text
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //updateText(settings.getString(getString(R.string.pref_webservice_endpoint_key), getString(R.string.pref_webservice_endpoint_default)));
        //textView.setTextColor(accentTextColor);

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

        // Register to receive messages
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter(EVENT_SET_OVERLAY_TEXT));
    }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onDestroy();

        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(textView);
        textView = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateText(final String extraText) {
        String overlayText = BuildConfig.FLAVOR + "-" + BuildConfig.BUILD_TYPE + " " + BuildConfig.VERSION_NAME;
        if (BuildConfig.DEBUG) {
            overlayText += extraText;
        }
        textView.setText(overlayText);
    }
}
