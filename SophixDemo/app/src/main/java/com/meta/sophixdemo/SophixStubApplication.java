package com.meta.sophixdemo;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {

    private final String TAG = "SophixStubApplication";

    /**
     * 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
     */
    @Keep
    @SophixEntry(AppApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
            // ignore
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("27771074",
                        "edc2451700aec180f204f81c3aa691fa",
                        "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCnqhmBy16Xoq9azl6pMUWqs9mai5BU0Hc7s+n167tQhTZzo1w7DytpoA0xNZca2/lWY6UgQBSZTEkTuvncMnSRxApOlXIEBJhg/nsO5AeQAQCc1P0k+rOx1pCWC+hLXxKADnTk2lGruc7djW7BTLWwjwAPnOXSL+Qm7+P3h9BSMLMc+T2RudZumxbGJnsMOSA3THUthRr85HKgpXWPxhXup0YK5NDHfIAYl0j/P/KWkI0Izc+TlLRu2yjN3CGHqBcXvdA6ZzLDAHQQEEEV8sdLlVKPcukwgDNX3buFKCsc5xVJL6DrLC2EgWJYSsJ6ZZ5wFPjYmXlzux5gZMjwbE+JAgMBAAECggEAXDBgVAjBmzjdmF1umTM5bjHzzVE97BSv6sjzNMAIP67vkMfbF7IwmaRifJNN4Xvgbkrd6YyyhtP2Pn4JkrS/ecLENA9iIKlyK6rheP+S1PBoKNPH2URr97XVzFsBwCenYXgReCA+1eCVySZSJsEKCemnC+6mQ1sH5Qz0WQ95UScECglvwLbmD7WqChrwnMo3XLKouhSygOwjYrpa2WFeePSg+Z+0gH66Nzb+E+8aACruEhiEYe7YMqaTtY7Wynfqvmva5XzujqX9KgytDuoboNUPMajYgah2JHgjsbbZjeeUGvfXIMMMpWigVgl2p6pAI0Mncmygw8PJD3AXS2DVzQKBgQD58k49TNB6NTpKL/a9uUpV7A7a5AGiTN36cxYsspED7cCnEo+tivLviM2hTaMVMuf8F/Igz8a9EKxRhQqi/KjisM3K2cIHEw/LZd5MbNgSKdR0brfdKqO/abZECngXh0S2Owpx4NsAqgIzwvHXQ1ybsgo6TFVEVqvGRyDBjsoDmwKBgQCruaL/AOmf7EG/m+Ja/yAtdHr64LWWYsinULhirPCWO1UMma9+wFyBOV7l6bagB0L9EuIStB+8GBDqXYmTcs1TFwEBQTBiYrEyxTE3Un0aoUpQAp9stSES4yl2Ot3rA57gP5kWkbNd2uSISJEHrjzAhTpCYjWB7bE6l+WokoulqwKBgFl0/8uw0mzq4BHtWMJOruxRsH9nW65bFJeu2SDmSEC/t1QUsJfMQ2a37+DriJLtxVnUuDRGKTtB3GrQ0x3CkC+SWBViGq+tgtmpfAaebjcSAZgqiGP//ttjWni/xnulTlJqjzJy5+806dTc0PHaiiqvbjmvWFU9L/2nN3IPSP5BAoGAT6wTCYic28u7PXDPdjSD81Llc9wpo5o1ShtTUMS34puoe+4Z1A7fXD0H4FesmVaFJ/FI3jCfwJ7RykqXd8d9p502pCRglGUBnXKLwQUOcfp/BdwRp1fp/l9Sbz4kk9LcNWKMXkI/G75g5TH1x8B5A0FMpFv4Q03KOksbDDjhfX0CgYBoPKF1beh6Uu7oeRffHEDbXFqHTGHyKxspE/uJUvrevVh4N2K6PZc8toUF6rLgjf9yDOOLecFAzl5jkg6p0+ylu1hlPd2jsbQjyTGA47/3r+msSY6TMJQPh9SbVqkLclfTYHCAXCy861e8Huy8CxiDnYyXZwrRXdSQpDhkrUnilg==")
                .setEnableDebug(false)
                .setEnableFullLog()
                .setPatchLoadStatusStub((mode, code, info, handlePatchVersion) -> {
                    Log.d(TAG, "sophix code: " + code + "  info: " + info + "  handlePatchVersion: " + handlePatchVersion);
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        Log.i(TAG, "sophix load patch success!");
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                    }
                }).initialize();
    }
}