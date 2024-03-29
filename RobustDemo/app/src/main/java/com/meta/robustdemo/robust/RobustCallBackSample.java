package com.meta.robustdemo.robust;

import android.util.Log;

import com.meituan.robust.Patch;
import com.meituan.robust.RobustCallBack;

import java.util.List;

/**
 * @author PingFu.Zhao
 * 2019/8/5
 */
public class RobustCallBackSample implements RobustCallBack {

    private static final String TAG = "RobustCallBackSample";

    @Override
    public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {
        Log.d(TAG, "onPatchListFetched result: " + result);
        Log.d(TAG, "onPatchListFetched isNet: " + isNet);
        for (Patch patch : patches) {
            Log.d(TAG, "onPatchListFetched patch: " + patch.getName());
        }
    }

    @Override
    public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
        Log.d(TAG, "onPatchFetched result: " + result);
        Log.d(TAG, "onPatchFetched isNet: " + isNet);
        Log.d(TAG, "onPatchFetched patch: " + patch.getName());
    }

    @Override
    public void onPatchApplied(boolean result, Patch patch) {
        Log.d(TAG, "onPatchApplied result: " + result);
        Log.d(TAG, "onPatchApplied patch: " + patch.getName());

    }

    @Override
    public void logNotify(String log, String where) {
        Log.d(TAG, "logNotify log: " + log);
        Log.d(TAG, "logNotify where: " + where);
    }

    @Override
    public void exceptionNotify(Throwable throwable, String where) {
        Log.e(TAG, "exceptionNotify where: " + where, throwable);
    }
}