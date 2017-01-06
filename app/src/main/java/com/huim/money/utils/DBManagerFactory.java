package com.huim.money.utils;

import android.content.Context;

import com.huim.money.bean.Shopping;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * 数据库
 */

public class DBManagerFactory {
    private static DBManagerFactory sIns;
    private static Context mContext;

    public static DBManagerFactory getIns(Context context) {
        if (sIns == null) {
            sIns = new DBManagerFactory();
        }
        mContext=context;
        return sIns;
    }

    private DbManager mDb;

    public void init(String dbName){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(dbName)
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager dbManager, int i, int i1) {

                    }
                });
        mDb = x.getDb(daoConfig);
        try {
            createTableIfNotExist(mDb, Shopping.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    protected void createTableIfNotExist(DbManager db, Class<?> entityClass) throws DbException {
        TableEntity<?> table = db.getTable(entityClass);
        SqlInfo sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(table);
        db.execNonQuery(sqlInfo);
    }

    public DbManager getDb() {
        if(mDb == null){
            init("ShoppingDb");
        }
        return mDb;
    }
}
