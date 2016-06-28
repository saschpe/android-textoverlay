package com.example.textoverlay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.textoverlay.R;

import saschpe.textoverlay.service.TextOverlayService;

public class MainActivity extends AppCompatActivity {
    private static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);


        /*// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.SYSTEM_ALERT_WINDOW)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                        ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }*/
    }

    public void setText(View view) {
        TextOverlayService.setText(this, editText.getText().toString());
    }
}
