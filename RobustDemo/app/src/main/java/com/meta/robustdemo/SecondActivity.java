package com.meta.robustdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnSecondBug = findViewById(R.id.btn_second_bug);

        // btnSecondBug.setText(getSecondText());
        btnSecondBug.setText(getSecondText());
    }

    private String getSecondText() {
        return "second bug";
    }
}