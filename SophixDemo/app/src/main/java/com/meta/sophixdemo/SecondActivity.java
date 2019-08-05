package com.meta.sophixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    /**
     * 跳转到第三个页面
     */
    private Button mBtnJumpToThirdActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mBtnJumpToThirdActivity = findViewById(R.id.btn_jump_to_third_activity);

        mBtnJumpToThirdActivity.setOnClickListener(v ->
                startActivity(new Intent(v.getContext(), ThirdActivity.class)));
    }
}