package com.meta.sophixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author zhaopf
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 跳转到第三个页面
        Button btnJumpToThirdActivity = findViewById(R.id.btn_jump_to_third_activity);
        // 吐司
        Button btnToast = findViewById(R.id.btn_toast);

        btnJumpToThirdActivity.setOnClickListener(v ->
                startActivity(new Intent(v.getContext(), ThirdActivity.class)));
        btnToast.setOnClickListener(v ->
                Toast.makeText(SecondActivity.this, "我是吐司。。", Toast.LENGTH_SHORT)
                        .show());
    }
}