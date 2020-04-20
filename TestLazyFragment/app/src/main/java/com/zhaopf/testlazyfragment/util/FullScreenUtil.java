package com.zhaopf.testlazyfragment.util;

import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * @author Shuai.Wong
 * 2018/11/23
 */
public class FullScreenUtil {

    public static void fullScreenSet(Window window) {
        if (window == null) {
            return;
        }
        fullScreenSet(window.getDecorView());
    }

    private static void fullScreenSet(View decorView) {
        if (decorView == null) {
            return;
        }
        int visibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        // 在小米手机上熄屏之后再打开，会被截掉状态栏高度的view
        if (!DeviceUtil.isMIUI()) {
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(visibility);
    }
}