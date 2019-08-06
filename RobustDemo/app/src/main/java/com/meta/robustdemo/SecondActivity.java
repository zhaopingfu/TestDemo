package com.meta.robustdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnSecondBug = findViewById(R.id.btn_second_bug);

        btnSecondBug.setText(getSecondText());

        btnSecondBug.setOnClickListener(v ->
                Toast.makeText(SecondActivity.this, "second bug.", Toast.LENGTH_SHORT)
                        .show()
        );
    }

    private String getSecondText() {
        return "second bug";
    }
}