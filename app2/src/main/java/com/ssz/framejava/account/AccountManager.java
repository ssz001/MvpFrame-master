package com.ssz.framejava.account;

import android.content.Context;

import com.ssz.framejava.base.cons.Constant;


/**
 * @author zsp
 * create at 2019/1/18 15:01
 * 账户相关的信息 存储，取出类
 */
public final class AccountManager {

    /**
     * 保存Token
     *
     * @param context ；
     * @param token TokenExpiredException
     */
    public static void saveToken(Context context, String token){
        PrefManager pre = new PrefManager(context);
        pre.saveValue(Constant.CON_TOKEN,token);
    }

    /**
     * 获取Token
     *
     * @param context ；
     * @return TokenExpiredException
     */
    public static String getToken(Context context) {
        PrefManager pre = new PrefManager(context);
        return pre.getValue(Constant.CON_TOKEN, "");
    }

    /**
     * 保存账户名
     *
     * @param context；
     * @param account 账户名
     */
    public static void saveAccount(Context context,String account){
        PrefManager pre = new PrefManager(context);
        pre.saveValue(Constant.CON_ACCOUNT,account);
    }

    /**
     * 获取账户
     *
     * @param context；
     * @return Account
     */
    public static String getAccount(Context context){
        PrefManager pre = new PrefManager(context);
        return pre.getValue(Constant.CON_ACCOUNT,"");
    }

    /**
     * 保存密码
     *
     * @param context；
     * @param password 密码
     */
    public static void savePassword(Context context,String password){
        PrefManager pre = new PrefManager(context);
        pre.saveValue(Constant.CON_PASSWORD,password);
    }

    /**
     * 获取密码
     *
     * @param context；
     * @return 密码
     */
    public static String getPassword(Context context){
        PrefManager pre = new PrefManager(context);
        return pre.getValue(Constant.CON_PASSWORD,"");
    }

    /**
     * 保存Token的有效时间
     *
     * @param context ；
     * @param tokenTime Token有效时间
     */
    public static void saveTokenTime(Context context,Long tokenTime){
        PrefManager pre = new PrefManager(context);
        pre.saveValue(Constant.CON_TOKEN_TIME,tokenTime);
    }

    /**
     * 获取Token有效时间
     *
     * @param context ；
     * @return TokenExpiredException 有效时间
     */
    public static Long getTokenTime(Context context){
        PrefManager pre = new PrefManager(context);
        return pre.getValue(Constant.CON_TOKEN_TIME,0L);
    }

    /**
     * 保存刷新Token
     *
     * @param context ；
     * @param refreshToken 刷新Token
     */
    public static void saveRefreshToken(Context context,String refreshToken){
        PrefManager pre = new PrefManager(context);
        pre.saveValue(Constant.CON_REFRESH_TOKEN,refreshToken);
    }

    /**
     * 获取刷新Token
     *
     * @param context；
     * @return 刷新Token
     */
    public static String getRefreshToken(Context context){
        PrefManager pre = new PrefManager(context);
        return pre.getValue(Constant.CON_REFRESH_TOKEN,"");
    }
}
