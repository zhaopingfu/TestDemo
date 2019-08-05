package com.meta.sophixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 这里延时，保证能够顺利获取到补丁信息
        getWindow().getDecorView().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 2_000);
    }
}