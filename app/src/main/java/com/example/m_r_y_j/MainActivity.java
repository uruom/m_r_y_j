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
    private Intent intent;

    private boolean isServiceRunning(final String className){
        ActivityManager activityManager =(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info =activityManager.getRunningServices(Integer.MAX_VALUE);
        if(info==null||info.size()==0) return false;
        for (ActivityManager.RunningServiceInfo aInfo:info){
            if(className.equals((aInfo.service.getClassName()))) return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      音乐
        if(isServiceRunning("com.example.m_r_y_j.MyService")==false){
            intent= new Intent(MainActivity.this,MyIntentService.class);
            String action = MyIntentService.ACTION_MUSIC;
            intent.setAction(action);
            startService(intent);
            stopService(intent);
        }








        // 链接组件
        m_title = (TextView) findViewById(R.id.tv_title);
        mBtn_instruction = (Button) findViewById(R.id.btn_instruction);
        mBtn_control = (Button) findViewById(R.id.btn_control);
        mBtn_start = (Button) findViewById(R.id.btn_start);
        mBtn_start_test = (Button) findViewById(R.id.btn_start_test);

        // 设置字体
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ziti_1.ttf");
        m_title.setTypeface(typeface);
        mBtn_start.setTypeface(typeface);
        mBtn_instruction.setTypeface(typeface);
        mBtn_control.setTypeface(typeface);
        mBtn_start_test.setTypeface(typeface);

        // 按钮监听器
        mBtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到运行界面
                intent= new Intent(MainActivity.this,MyIntentService.class);
                String action = MyIntentService.ACTION_MUSIC;
                intent.setAction(action);
                stopService(intent);
                Intent intent = new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
//                stopService(intent);
            }
        });
        mBtn_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到说明界面
                intent= new Intent(MainActivity.this,MyIntentService.class);
                String action = MyIntentService.ACTION_MUSIC;
                intent.setAction(action);
                stopService(intent);
                Intent intent = new Intent(MainActivity.this,InstructionActivity.class);
                startActivity(intent);
            }
        });
        mBtn_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到设置界面
                intent= new Intent(MainActivity.this,MyIntentService.class);
                String action = MyIntentService.ACTION_MUSIC;
                intent.setAction(action);
                stopService(intent);
                Intent intent = new Intent(MainActivity.this,ControlActivity.class);
                startActivity(intent);
//                stopService(intent);
            }
        });


        mBtn_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetectActivity.class);
                startActivity(intent);
            }
        });
    }

}