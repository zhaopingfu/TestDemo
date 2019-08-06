package com.meta.robustdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnSecondBug = findViewById(R.id.btn_second_bug);

        btnSecondBug.setText(getSecondText());

        btnSecondBug.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "second bug.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSecondText() {
        return "second bug";
    }
}