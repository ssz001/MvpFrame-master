package com.ssz.studydemo.data.local

import android.database.sqlite.SQLiteDatabase
import com.ssz.studydemo.app.AppContext
import com.ssz.studydemo.model.dao.DaoMaster
import com.ssz.studydemo.model.dao.DaoSession


/**
 * @author : zsp
 * time : 2019 09 2019/9/17 14:28
 */
class DbHelper private constructor(){

    val mDb: SQLiteDatabase
    val mDaoMaster: DaoMaster
    val mSession: DaoSession

    companion object {
        private const val DB_NAME = "ssz_note"
        val INSTANCE : DbHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            DbHelper()
        }
    }

    init {
        val openHelper = MyOpenHelper(AppContext.getInstance(), DB_NAME, null)
        //获取数据库
        mDb = openHelper.writableDatabase
        //封装数据库中表的创建、更新、删除
        mDaoMaster = DaoMaster(mDb)  //合起来就是对数据库的操作
        //对表操作的对象。
        mSession = mDaoMaster.newSession() //可以认为是对数据的操作
    }

    fun getNewSession(): DaoSession {
        return mDaoMaster.newSession()
    }

}