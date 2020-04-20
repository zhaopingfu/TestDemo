package com.zhaopf.testlazyfragment.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DeviceUtil {

    /**
     * 设置MIUI状态栏的模式
     */
    public static void setMIUIStatusBarLightMode(Window window, boolean dark) {
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                @SuppressLint("PrivateApi") Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    // 状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    // 清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * 是否是小米
     */
    public static boolean isMIUI() {
        return DeviceUtil.getRomType() == ROM_TYPE.MIUI;
    }

    /**
     * 是否是魅族
     */
    public static boolean isFlyme() {
        return DeviceUtil.getRomType() == DeviceUtil.ROM_TYPE.FLYME;
    }

    /**
     * 获取ROM类型: MIUI_ROM, FLYME_ROM, EMUI_ROM, OTHER_ROM
     */
    private static ROM_TYPE getRomType() {
        try {
            String brand = getROMName();
            String s = brand.toLowerCase();
            if (s.contains("xiaomi")) {
                // 小米 红米
                return ROM_TYPE.MIUI;
            } else if (s.contains("huawei") || s.contains("honor")) {
                // 华为 honor
                return ROM_TYPE.EMUI;
            } else if (s.contains("meizu")) {
                // 魅族
                return ROM_TYPE.FLYME;
            } else {
                return ROM_TYPE.OTHER;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ROM_TYPE.OTHER;
        }
    }

    public enum ROM_TYPE {
        /**
         * 小米
         */
        MIUI,
        /**
         * 魅族
         */
        FLYME,
        /**
         * 华为
         */
        EMUI,
        /**
         * 其他
         */
        OTHER
    }

    /**
     * Rom厂商
     */
    public static String getROMName() {
        return Build.MANUFACTURER;
    }
}