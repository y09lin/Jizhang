package com.huim.money.utils;

import android.content.Context;

import com.huim.money.bean.Shopping;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物数据存储
 */

public class DbShopping {
    public static boolean saveItem(Context context, Shopping shopping){
        DbManager dbManager=DBManagerFactory.getIns(context).getDb();
        if (dbManager!=null && shopping!=null){
            try {
                dbManager.saveOrUpdate(shopping);
                return true;
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<Shopping> getShoppings(Context context){
        List<Shopping> list=new ArrayList<>();
        DbManager dbManager=DBManagerFactory.getIns(context).getDb();
        if (dbManager!=null){
            try {
                list=dbManager.findAll(Shopping.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
