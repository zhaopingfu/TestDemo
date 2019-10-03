package com.example.testhotfix.fixmethod;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class FixUtil {

    private static final String TAG = "FixUtil";

    public static void fix(@NonNull Context context, String dexPath) {
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

            // 根据新的 dex 文件创建 dexElements
            FixMethodInterface fixMethod = FixMethodFactory.getFixMethod();
            if (null == fixMethod) {
                Log.d(TAG, "fix: 不支持修复");
                return;
            }
            Object[] newDexElements = fixMethod.getElements(pathList, context, dexPath);
            // 合并两个 dexElements
            Object[] resultElements = (Object[]) Array.newInstance(oldDexElements.getClass().getComponentType(), oldDexElements.length + newDexElements.length);
            System.arraycopy(newDexElements, 0, resultElements, 0, newDexElements.length);
            System.arraycopy(oldDexElements, 0, resultElements, newDexElements.length, oldDexElements.length);
            // 重新设置 dexElements
            dexElementsField.set(pathList, resultElements);
            if (new File(dexPath).exists()) {
                Log.d(TAG, "fix: 修复成功 ");
            } else {
                Log.d(TAG, "fix: 修复包不存在: " + dexPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "fix: 修复失败: " + e.getMessage());
        }
    }

    static File getHackDex(Context context) {
        File hackDir = context.getDir("hack", Context.MODE_PRIVATE);
        File hackFile = new File(hackDir, "hack.dex");
        if (hackFile.exists()) {
            return hackFile;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(context.getAssets().open("hack.dex"));
            bos = new BufferedOutputStream(new FileOutputStream(hackFile));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hackFile;
    }
}