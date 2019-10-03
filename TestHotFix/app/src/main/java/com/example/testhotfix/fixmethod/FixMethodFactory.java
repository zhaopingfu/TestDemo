package com.example.testhotfix.fixmethod;

import android.os.Build;

import androidx.annotation.Nullable;

public class FixMethodFactory {
    private FixMethodFactory() {
    }

    @Nullable
    public static FixMethodInterface getFixMethod() {
        // 6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new FixMethod60Impl();
        }
        // 4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new FixMethod44Impl();
        }
        // 4.1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new FixMethod41Impl();
        }
        return null;
    }
}