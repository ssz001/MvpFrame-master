package com.ssz.studydemo.data.local2;

import android.content.Context;

import com.ssz.frame.utils.Framework;

/**
 * @author : zsp
 * time : 2019 10 2019/10/14 14:36
 * 1.需要更改数据库名字的情况，例如多用户登录
 * 2.就多数据库的情况
 */
public final class DbManager {

    /**
     * 数据库名字
     */
    private static String db_default = "db_default";
    private static String db_other = "db_other";

    private static volatile DbManager instance;

    private final DbHelper mDefault;
//  private final DbHelper mOther;

    public static DbManager getInstance(){
        if (null == instance){
            synchronized (DbManager.class){
                if (null == instance){
                    instance = new DbManager();
                }
            }
        }
        return instance;
    }

    public static DbHelper getDefault(){
        return getInstance().mDefault;
    }

//  public static DbHelper getOther(){
//        return getInstance().mOther;
//  }

    private DbManager(){
        final Context appContext = Framework.context;
        mDefault = new DbHelper
                .Builder(appContext,db_default)
                .writeAble(true)
                .build();

        //add 添加 有多个就新建多个
//        mOther = new DbHelper
//                .Builder(appContext,db_other)
//                .writeAble(true)
//                .build();

    }

    /**
     * 设置数据库名
     */
    public static void setDbNameDefault(String value){
        db_default = value;
    }

    /**
     * 未测试过
     */
    public static void release(){
       DbManager.instance.mDefault.close();
//       DbManager.instance.mOther.close();
       DbManager.instance = null;
    }
}
