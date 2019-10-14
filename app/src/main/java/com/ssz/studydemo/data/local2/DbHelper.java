package com.ssz.studydemo.data.local2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssz.studydemo.data.local.DbOpenHelper;
import com.ssz.studydemo.model.dao.DaoMaster;
import com.ssz.studydemo.model.dao.DaoSession;

/**
 * @author : zsp
 * time : 2019 10 2019/10/14 14:47
 */
public final class DbHelper {

    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;
    private DbOpenHelper openHelper;

    private DbHelper(Builder builder) {
        openHelper = new DbOpenHelper(builder.context, builder.name, builder.factory);
        mDb = builder.writeAble ? openHelper.getWritableDatabase() : openHelper.getReadableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mSession = mDaoMaster.newSession();
    }

    public SQLiteDatabase getDatabase() {
        return mDb;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mSession;
    }

    public DaoSession getNewSession() {
        return mDaoMaster.newSession();
    }

    /**
     * 不知道干嘛的
     */
    public void close(){
        openHelper.close();
    }

    public static class Builder {

        private final Context context;
        private final String name;
        private SQLiteDatabase.CursorFactory factory = null;
        private boolean writeAble;

        public Builder(Context context, String name) {
            this.context = context;
            this.name = name;
        }

        public Builder writeAble(boolean writeAble) {
            this.writeAble = writeAble;
            return this;
        }

        public Builder curFactory(SQLiteDatabase.CursorFactory factory) {
            this.factory = factory;
            return this;
        }

        public DbHelper build() {
            return new DbHelper(this);
        }
    }
}
