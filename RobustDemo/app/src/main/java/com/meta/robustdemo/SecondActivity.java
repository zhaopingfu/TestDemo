package com.meta.robustdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    @Add
    public static class SecondActivityClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "second bug fixed.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}