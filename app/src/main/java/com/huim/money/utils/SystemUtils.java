package com.huim.money.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

/**
 * 系统相关方法
 */

public class SystemUtils {

    public static String getAppId(Context context){
        return context.getPackageName();
    }

    public static String getAppVersion(Context context){
        return getAppVersion(context,getAppId(context));
    }

    public static String getAppVersion(Context context,String appId){
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(appId, 0).versionName;
            if(versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
