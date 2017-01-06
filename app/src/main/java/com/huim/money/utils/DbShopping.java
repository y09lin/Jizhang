package com.huim.money.utils;

import android.content.Context;

import com.huim.money.bean.Shopping;

import org.xutils.DbManager;

/**
 * 购物数据存储
 */

public class DbShopping {
    public static boolean saveItem(Context context, Shopping shopping){
        DbManager dbManager=DBManagerFactory.getIns(context).getDb();
        if (dbManager!=null){}
        return false;
    }
}
