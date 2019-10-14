package com.ssz.studydemo.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ssz.studydemo.model.dao.DaoMaster

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 14:44
 */
class DbOpenHelper(context: Context, name :String, factory : SQLiteDatabase.CursorFactory?)
    : DaoMaster.OpenHelper(context, name, factory) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        when (oldVersion) {
            1,
                // 暂无 1.0
            2 -> {

            }
            // 更新数据到 3.0
            else -> {

            }
        }
    }
}