package com.meta.sophixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 2_000);

        throw new NullPointerException(TAG + " ---> 故意抛出来的异常");
    }
}