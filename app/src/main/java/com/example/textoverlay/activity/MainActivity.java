package com.example.textoverlay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.textoverlay.R;

import saschpe.textoverlay.service.TextOverlayService;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
    }

    public void replaceText(View view) {
        /*
        Intent intent = new Intent(TextOverlayService.ACTION_SET_TEXT);
        intent.putExtra(TextOverlayService.EXTRA_TEXT, editText.getText());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        */

        // Or, even simpler:
        TextOverlayService.setText(this, editText.getText());
    }
}
