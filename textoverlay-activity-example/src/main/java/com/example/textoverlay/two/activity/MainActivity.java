package com.example.textoverlay.two.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.textoverlay.two.R;
import com.example.textoverlay.two.activity.base.BaseActivity;

import saschpe.textoverlay.service.TextOverlayService;

public class MainActivity extends BaseActivity {
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

    public void startOtherActivity(View view) {
        startActivity(new Intent(this, OtherActivity.class));
    }
}
