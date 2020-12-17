package com.zhaopf.testlazyfragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhaopf.testlazyfragment.util.DeviceUtil;
import com.zhaopf.testlazyfragment.util.FullScreenUtil;
import com.zhaopf.testlazyfragment.util.NotchSizeUtil;

/**
 * @author zhaopf
 * @date 2019-08-08
 */
public class FullScreenObserver implements LifecycleObserver {

    private AppCompatActivity mAppCompatActivity;
    /**
     * 是否全屏模式
     */
    private boolean isFullScreenMode = true;

    public FullScreenObserver(@NonNull AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;

        mAppCompatActivity.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mAppCompatActivity.getLifecycle().removeObserver(this);
        mAppCompatActivity = null;
    }

    /**
     * 全屏沉浸
     */
    public void fullScreen(boolean isFullScreenMode) {
        this.isFullScreenMode = isFullScreenMode;
    }

    public void setStatusBarTranslate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperApi21();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperApi19();
        }
    }

    /**
     * 5.0版本以上
     */
    private void setStatusBarUpperApi21() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Window window = mAppCompatActivity.getWindow();
        // 设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup mContentView = mAppCompatActivity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            // 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            mChildView.setFitsSystemWindows(false);
        }
    }

    /**
     * 设置OPPO手机状态栏字体为黑色(colorOS3.0,6.0以下部分手机)
     */
    private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

    /**
     * 4.4 - 5.0版本
     */
    private void setStatusBarUpperApi19() {
        Window window = mAppCompatActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mAppCompatActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(mAppCompatActivity);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarAlpha(1);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resId = mAppCompatActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = mAppCompatActivity.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    /**
     * @param colorRes 这个参数暂时没有用，不知道当时为什么弄这个变量
     */
    public void applyKitKatTranslucencyWithColor(int colorRes) {
        // 如果是华为的 8.0或 8.1的刘海屏  就拿到decorView 的第一个子view (是LinearLayout)
        // 在它的顶部add 一个黑色的Linearlayout(如果已经存在的话，就不加了) 为了把我们的contentView 顶下去
        if (NotchSizeUtil.hasNotchInScreen(mAppCompatActivity) && Build.VERSION.SDK_INT <= 27) {
            ViewGroup decorView = (ViewGroup) mAppCompatActivity.getWindow().getDecorView();
            LinearLayout childLinear = (LinearLayout) decorView.getChildAt(0);

            LinearLayout linearLayout = new LinearLayout(mAppCompatActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            linearLayout.setLayoutParams(lp);
            linearLayout.setBackgroundColor(Color.BLACK);
            if (childLinear.getChildAt(0) instanceof LinearLayout) {
                return;
            } else {
                childLinear.addView(linearLayout, 0);
            }
            return;
        }
        // 这行代码暂时没有用，不知道当时为什么弄这个变量
        // int mColorRes = colorRes;
        // KitKat translucent navigation/status bar.
        if (isFullScreenMode) {
            // 全屏-隐藏statusBar和navigationBar的
            Window window = mAppCompatActivity.getWindow();
            FullScreenUtil.fullScreenSet(window);
        } else {
            // 全屏-只隐藏statusBar的，隐藏navigationBar的
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            mAppCompatActivity.getWindow().getDecorView().setSystemUiVisibility(visibility);
        }
    }

    public void applyKitKatTranslucencyColor(int color) {
        //onDestroy 会把这个对象置空
        if (mAppCompatActivity == null) {
            return;
        }
        // KitKat translucent navigation/status bar.
        if (isFullScreenMode) {
            Window window = mAppCompatActivity.getWindow();
            FullScreenUtil.fullScreenSet(window);
            return;
        }
        if ("oppo".equalsIgnoreCase(DeviceUtil.getROMName())
                || "vivo".equalsIgnoreCase(DeviceUtil.getROMName())) {
            setOppoStatusBarTextColor(color, color != R.color.lucky_toolbar_brown
                    && color != R.color.black, mAppCompatActivity);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(mAppCompatActivity);
            mTintManager.setStatusBarTintEnabled(true);

            // 判断是否为小米手机
            if (DeviceUtil.isMIUI() || DeviceUtil.isFlyme()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mAppCompatActivity.getWindow().getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                mTintManager.setStatusBarTintColor(color);
                DeviceUtil.setMIUIStatusBarLightMode(mAppCompatActivity.getWindow(), true);
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    // 通知栏所需颜色
                    mTintManager.setStatusBarTintColor(
                            ContextCompat.getColor(mAppCompatActivity, R.color.white));
                    // 去除半透明状态栏
                    mAppCompatActivity.getWindow()
                            .clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // 一般配合fitsSystemWindows()使用,
                    // 或者在根部局加上属性android:fitsSystemWindows="true", 使根部局全屏显示
                    mAppCompatActivity.getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                } else {
                    mTintManager.setStatusBarTintColor(color);
                    mAppCompatActivity.getWindow()
                            .clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // 去除半透明状态栏
                    mAppCompatActivity.getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    // 一般配合fitsSystemWindows()使用,
                    // 或者在根部局加上属性android:fitsSystemWindows="true", 使根部局全屏显示
                    mAppCompatActivity.getWindow()
                            .setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
    }

    private static void setOppoStatusBarTextColor(int color, boolean lightStatusBar, Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        int vis = window.getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightStatusBar) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.setBackgroundDrawable(new ColorDrawable(color));
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.setBackgroundDrawable(new ColorDrawable(color));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (lightStatusBar) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                window.setBackgroundDrawable(new ColorDrawable(color));
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                window.setBackgroundDrawable(new ColorDrawable(color));
            }
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }

    public void applyKitKatTranslucency() {
        applyKitKatTranslucencyWithColor(R.color.white);
    }

    private void setTranslucentStatus(boolean on) {
        Window win = mAppCompatActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}