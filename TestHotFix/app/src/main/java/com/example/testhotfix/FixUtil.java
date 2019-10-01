package com.example.testhotfix;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FixUtil {

    private static final String TAG = "FixUtil";

    public static void fix(@NonNull Context context, String dexPath) {
        File file = new File(dexPath);
        if (!file.exists()) {
            Log.d(TAG, "fix: 没有新的dex文件: " + file.getAbsolutePath());
            return;
        }
        File cacheDir = context.getCacheDir();
        Log.d(TAG, "fix: cacheDir: " + cacheDir.getAbsolutePath());
        try {
            ClassLoader classLoader = context.getClassLoader();
            // 拿到 pathList
            Field pathListField = ReflectUtil.getField(classLoader, "pathList");
            Object pathList = pathListField.get(classLoader);
            // 拿到 dexElements
            Field dexElementsField = ReflectUtil.getField(pathList, "dexElements");
            Object[] oldDexElements = (Object[]) dexElementsField.get(pathList);
            // 获取  makePathElements 方法
            Method makePathElementsMethod = ReflectUtil.getMethod(pathList, "makePathElements", List.class, File.class, List.class);
            // 根据新的 dex 文件创建 dexElements
            List<File> files = new ArrayList<>();
            files.add(file);
            List<IOException> suppressedExceptions = new ArrayList<>();
            Object[] newDexElements = (Object[]) makePathElementsMethod.invoke(null, files, cacheDir, suppressedExceptions);
            // 合并两个 dexElements
            Object[] resultElements = (Object[]) Array.newInstance(oldDexElements.getClass().getComponentType(), oldDexElements.length + newDexElements.length);
            System.arraycopy(newDexElements, 0, resultElements, 0, newDexElements.length);
            System.arraycopy(oldDexElements, 0, resultElements, newDexElements.length, oldDexElements.length);
            // 重新设置 dexElements
            dexElementsField.set(pathList, resultElements);
            Log.d(TAG, "fix: 修复成功 ");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "fix: 修复失败: " + e.getMessage());
        }
    }
}