package com.example.m_r_y_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructionActivity extends AppCompatActivity {

    private Button mbtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        mbtn_back = (Button) findViewById(R.id.btn_instruction_back);
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}