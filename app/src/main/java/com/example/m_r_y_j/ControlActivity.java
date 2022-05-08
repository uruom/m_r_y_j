package com.example.m_r_y_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ControlActivity extends AppCompatActivity {

    private TextView mtv_title;
    private Button mbtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ziti_1.ttf");
        mtv_title = (TextView) findViewById(R.id.tv_control_title);
        mtv_title.setTypeface(typeface);
        mbtn_back =(Button) findViewById(R.id.btn_control_back);
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}