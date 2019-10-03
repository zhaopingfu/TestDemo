package com.example.testhotfix.fixmethod;

import android.content.Context;

import androidx.annotation.Nullable;

public interface FixMethodInterface {

    /**
     * @param pathList DexPathList 对象
     * @param context  获取私有目录和classLoader
     * @param dexPath  新的dex路径
     * @return 新的 dexElements
     */
    @Nullable
    Object[] getElements(Object pathList, Context context, String dexPath);
}