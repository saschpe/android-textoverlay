package com.example.textoverlay.two.activity.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.example.textoverlay.two.BuildConfig;

import saschpe.textoverlay.service.TextOverlayService;

/**
 * Example base activity that implements text overlay handling.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String overlayText = BuildConfig.APPLICATION_ID + " (" + BuildConfig.VERSION_NAME + " - "
                + BuildConfig.BUILD_TYPE + ")";

        Intent intent = new Intent(this, TextOverlayService.class);
        intent.putExtra(TextOverlayService.EXTRA_TEXT, overlayText);

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                startService(intent);
            }
        } else {
            startService(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopService(new Intent(this, TextOverlayService.class));
    }
}
