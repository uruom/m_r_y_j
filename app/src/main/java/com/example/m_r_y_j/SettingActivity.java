package com.example.m_r_y_j;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private TextView mtv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mtv_title = findViewById(R.id.tv_control_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ziti_1.ttf");
        mtv_title.setTypeface(typeface);
    }
}