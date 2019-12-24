package com.ssz.baselibrary.model.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssz.baselibrary.model.local.dao.DaoMaster;


public class DbOpenHelper extends DaoMaster.OpenHelper {

    DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:

                break;
            case 2:

                break;
            default:
                break;
        }
    }
}
