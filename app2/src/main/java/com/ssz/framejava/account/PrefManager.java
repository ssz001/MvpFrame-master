package com.ssz.framejava.account;

import android.content.Context;
import android.content.SharedPreferences;

import com.ssz.framejava.cons.Constant;

import java.util.Iterator;
import java.util.Map;

/**
 * @author zsp
 * create at 2019/1/18 17:07
 * SharedPreferences 的 使用封装。
 */
public class PrefManager implements IPrefManager {

    private final SharedPreferences preferences;

    public PrefManager(Context context) {
        // TODO Auto-generated constructor stub
        preferences = context.getSharedPreferences(Constant.CON_ADDRESS, Context.MODE_PRIVATE);
    }

    @Override
    public void saveValue(String key, Object value) {
        // TODO Auto-generated method stub
        SharedPreferences.Editor editor = preferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    @Override
    public <T extends Object> T getValue(String key, T defaultValue) {
        // TODO Auto-generated method stub
        Map<String, T> map = (Map<String, T>) preferences.getAll();
        T value = map.get(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public void removeValue(String key) {
        // TODO Auto-generated method stub
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    @Override
    public void removeAllValues() {
        // TODO Auto-generated method stub
        Map<String, ?> map = preferences.getAll();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            removeValue(iterator.next());
        }
    }
}
