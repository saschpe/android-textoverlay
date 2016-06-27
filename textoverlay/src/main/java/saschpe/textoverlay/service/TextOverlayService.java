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

    public static final String ACTION_SET_TEXT = "saschpe.textoverlay.service.SET_TEXT";
    public static final String EXTRA_TEXT = "saschpe.textoverlay.service.text";

    private TextView textView;

    /**
     * Helper method that simplifies updating overlay text.
     *
     * @param context A context.
     * @param text The new text.
     */
    public static void setText(final Context context, final CharSequence text) {
        Intent intent = new Intent(TextOverlayService.ACTION_SET_TEXT);
        intent.putExtra(TextOverlayService.EXTRA_TEXT, text);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            final String message = intent.getStringExtra(EXTRA_TEXT);
            if (message != null) {
                Log.d(TAG, "onReceive() Got message: " + message);
                setText(message);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        textView = new TextView(this);

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
                new IntentFilter(ACTION_SET_TEXT));
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

    private void setText(final String extraText) {
        String overlayText = BuildConfig.FLAVOR + "-" + BuildConfig.BUILD_TYPE + " " + BuildConfig.VERSION_NAME;
        if (BuildConfig.DEBUG) {
            overlayText += extraText;
        }
        textView.setText(overlayText);
    }
}
