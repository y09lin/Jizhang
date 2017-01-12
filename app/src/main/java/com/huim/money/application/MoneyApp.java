package com.huim.money.application;

import android.app.Application;
import android.os.Handler;

import com.huim.money.BuildConfig;
import com.huim.money.utils.ToastUtil;

import org.xutils.x;

public class MoneyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this, new Handler());
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
