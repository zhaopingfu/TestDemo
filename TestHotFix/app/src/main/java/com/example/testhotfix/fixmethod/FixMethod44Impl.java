package com.example.testhotfix.fixmethod;

import android.content.Context;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class FixMethod44Impl implements FixMethodInterface {

    @Nullable
    @Override
    public Object[] getElements(Object pathList, Context context, String dexPath) {
        File file = new File(dexPath);
        // 根据新的 dex 文件创建 dexElements
        ArrayList<File> files = new ArrayList<>();
        files.add(FixUtil.getHackDex(context));
        if (file.exists()) {
            files.add(file);
        }
        ArrayList<IOException> suppressedExceptions = new ArrayList<>();
        try {
            // 获取  makePathElements 方法
            Method makePathElementsMethod = ReflectUtil.getMethod(pathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class);
            Object[] newDexElements = (Object[]) makePathElementsMethod.invoke(null, files, context.getCacheDir(), suppressedExceptions);
            return newDexElements;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}