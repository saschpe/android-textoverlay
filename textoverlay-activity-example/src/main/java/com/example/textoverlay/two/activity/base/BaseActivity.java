/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
