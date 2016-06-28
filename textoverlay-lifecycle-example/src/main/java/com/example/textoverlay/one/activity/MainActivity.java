package com.example.textoverlay.one.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.textoverlay.one.R;

import saschpe.textoverlay.service.TextOverlayService;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
    }

    public void setText(View view) {
        TextOverlayService.setText(this, editText.getText().toString());
    }
}
