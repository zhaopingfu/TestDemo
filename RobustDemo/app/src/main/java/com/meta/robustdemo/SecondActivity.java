package com.meta.robustdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.meituan.robust.patch.annotaion.Add;
import com.meituan.robust.patch.annotaion.Modify;

public class SecondActivity extends AppCompatActivity {

    @Modify
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnSecondBug = findViewById(R.id.btn_second_bug);

        // btnSecondBug.setText(getSecondText());
        btnSecondBug.setText(getSecondText2());

        btnSecondBug.setOnClickListener(new SecondActivityClickListener());
    }

    private String getSecondText() {
        return "second bug";
    }

    @Add
    private String getSecondText2() {
        StringBuilder sb = new StringBuilder();
        for (String s : getStrArray()) {
            sb.append(s);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Add
    public String[] getStrArray() {
        return new String[]{"hello", "world"};
    }
}