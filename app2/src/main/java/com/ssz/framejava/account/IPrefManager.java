package com.ssz.framejava.account;


/**
 * @author zsp
 * create at 2019/1/18 17:07
 * SharedPreferences工具类的顶层接口抽象
 */
public interface IPrefManager {

    <T extends Object> T getValue(String key, T defaultValue);

    void saveValue(String key, Object value);

    void removeValue(String key);

    void removeAllValues();
}
