package com.meta.robustdemo;

import android.view.View;
import android.widget.Toast;

import com.meituan.robust.patch.annotaion.Add;

@Add
public class SecondActivityClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "second bug fixed.", Toast.LENGTH_SHORT)
                .show();
    }
}