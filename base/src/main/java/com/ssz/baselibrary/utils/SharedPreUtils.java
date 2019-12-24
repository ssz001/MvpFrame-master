package com.ssz.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ssz.baselibrary.app.helper.AppHelper;

/**
 * Created by newbiechen on 17-4-16.
 */

public final class SharedPreUtils {
    private static final String SHARED_NAME = "ssz_pref";
    private static volatile SharedPreUtils sInstance;
    private final SharedPreferences sharedPreference;

    private SharedPreUtils(){
        sharedPreference = AppHelper.getApplication()
                .getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreUtils get(){
        if(sInstance == null){
            synchronized (SharedPreUtils.class){
                if (sInstance == null){
                    sInstance = new SharedPreUtils();
                }
            }
        }
        return sInstance;
    }

    public void putString(String key, String value){
        SharedPreferences.Editor write = sharedPreference.edit();
        write.putString(key,value);
        write.apply();
    }

    public String getString(String key,String def){
        return sharedPreference.getString(key,def);
    }

    public String getString(String key){
        return sharedPreference.getString(key,"");
    }

    public void putInt(String key, int value){
        SharedPreferences.Editor write = sharedPreference.edit();
        write.putInt(key, value);
        write.apply();
    }

    public int getInt(String key, int def){
        return sharedPreference.getInt(key, def);
    }

    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor write = sharedPreference.edit();
        write.putBoolean(key, value);
        write.apply();
    }

    public boolean getBoolean(String key, boolean def){
        return sharedPreference.getBoolean(key, def);
    }
}
