package com.example.m_r_y_j;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView m_title;
    private Button mBtn_start,mBtn_instruction,mBtn_control,mBtn_start_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 链接组件
        m_title = findViewById(R.id.tv_title);
        mBtn_instruction = findViewById(R.id.btn_instruction);
        mBtn_control = findViewById(R.id.btn_control);
        mBtn_start = findViewById(R.id.btn_start);
        mBtn_start_test = findViewById(R.id.btn_start_test);

        // 设置字体
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ziti_1.ttf");
        m_title.setTypeface(typeface);
        mBtn_start.setTypeface(typeface);
        mBtn_instruction.setTypeface(typeface);
        mBtn_control.setTypeface(typeface);
        mBtn_start_test.setTypeface(typeface);

        // 跳转到运行界面
        mBtn_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(intent);
        });

        // 跳转到使用说明界面
        mBtn_instruction.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,InstructionActivity.class);
            startActivity(intent);
        });

        //跳转到设置界面
        mBtn_control.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,ControlActivity.class);
            startActivity(intent);
        });

        // 跳转到运行界面
        mBtn_start_test.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DetectActivity.class);
            startActivity(intent);
        });
    }

}