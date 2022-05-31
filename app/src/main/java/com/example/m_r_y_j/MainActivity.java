package com.example.m_r_y_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView m_title;
    private Button mBtn_start,mBtn_instruction,mBtn_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 链接组件
        m_title = findViewById(R.id.tv_title);
        mBtn_start = findViewById(R.id.btn_start);
        mBtn_instruction = findViewById(R.id.btn_instruction);
        mBtn_settings = findViewById(R.id.btn_control);

        // 设置字体
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ziti_1.ttf");
        m_title.setTypeface(typeface);
        mBtn_start.setTypeface(typeface);
        mBtn_instruction.setTypeface(typeface);
        mBtn_settings.setTypeface(typeface);

        // 跳转到运行界面
        mBtn_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DetectActivity.class);
            startActivity(intent);
        });

        // 跳转到使用说明界面
        mBtn_instruction.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,InstructionActivity.class);
            startActivity(intent);
        });

        //跳转到设置界面
        mBtn_settings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

}