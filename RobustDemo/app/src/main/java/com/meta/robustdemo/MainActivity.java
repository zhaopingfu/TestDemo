package com.meta.robustdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.meituan.robust.PatchExecutor;
import com.meta.robustdemo.robust.PatchManipulateImp;
import com.meta.robustdemo.robust.PermissionUtils;
import com.meta.robustdemo.robust.RobustCallBackSample;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_patch).setOnClickListener(this);

        findViewById(R.id.btn_to_second_activity).setOnClickListener(this);

        findViewById(R.id.btn_bug).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_patch: {
                if (isGrantSDCardReadPermission()) {
                    runRobust();
                } else {
                    requestPermission();
                }
            }
            break;
            case R.id.btn_to_second_activity: {
                startActivity(new Intent(v.getContext(), SecondActivity.class));
            }
            break;
            case R.id.btn_bug: {
                Toast.makeText(MainActivity.this, "main bug fixed222.", Toast.LENGTH_SHORT)
                        .show();
            }
            break;
            default:
                break;
        }
    }

    private boolean isGrantSDCardReadPermission() {
        return PermissionUtils.isGrantSDCardReadPermission(this);
    }

    private void requestPermission() {
        PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SDCARD_READ);
    }

    private static final int REQUEST_CODE_SDCARD_READ = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_SDCARD_READ) {
            handlePermissionResult();
        }
    }

    private void handlePermissionResult() {
        if (isGrantSDCardReadPermission()) {
            runRobust();
        } else {
            Toast.makeText(this, "failure because without sd card read permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void runRobust() {
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBackSample()).start();
    }
}