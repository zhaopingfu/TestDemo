package com.meta.sophixdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.taobao.sophix.SophixManager;

/**
 * @author metaapp
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 当前的数据
     */
    private int mCurrNum = 0;
    /**
     * 按钮
     */
    private Button mBtnAddOne;
    /**
     * 跳转到第二个页面
     */
    private Button mBtnJumpToSecondActivity;
    /**
     * 检查补丁
     */
    private Button mBtnCheckFixBag;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();

        mBtnAddOne.setOnClickListener(v -> {
            mCurrNum = add(mCurrNum);
            mBtnAddOne.setText("计算 +1 结果: " + mCurrNum);
        });

        mBtnJumpToSecondActivity.setOnClickListener(v ->
                startActivity(new Intent(v.getContext(), SecondActivity.class)));

        mBtnCheckFixBag.setOnClickListener(v -> {
            // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
            SophixManager.getInstance().queryAndLoadNewPatch();
        });
    }

    private void findViewByIds() {
        mBtnAddOne = findViewById(R.id.btn_add_one);
        mBtnJumpToSecondActivity = findViewById(R.id.btn_jump_to_second_activity);
        mBtnCheckFixBag = findViewById(R.id.btn_check_fix_bag);
    }

    /**
     * 加法
     */
    private int add(int num) {
        return num - 1;
    }
}