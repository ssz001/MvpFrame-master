package com.ssz.framejava.model.local;

import android.database.sqlite.SQLiteDatabase;

import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.model.local.dao.DaoMaster;
import com.ssz.framejava.model.local.dao.DaoSession;

public final class DbHelper {

    private static volatile DbHelper instance;
    private static final String DB_NAME = "ssz_db_note";

    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;

    public static DbHelper getInstance() {
        if (null == instance){
            synchronized (DbHelper.class){
                if (null == instance){
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    private DbHelper(){
       DbOpenHelper openHelper = new DbOpenHelper(AppHelper.getApplication(),DB_NAME,null);
       mDb = openHelper.getWritableDatabase();
       mDaoMaster = new DaoMaster(mDb);
       mSession = mDaoMaster.newSession();
    }


    public DaoSession getNewSession(){
        return mDaoMaster.newSession();
    }
}
