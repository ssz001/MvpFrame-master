package com.ssz.framejava.model.remote.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.ssz.framejava.app.Framework;
import com.ssz.framejava.utils.log.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 11:09
 */
public final class URL {
    public static  String BASE_URL = "http://192.168.0.100:8888";

    /**
     * 登录
     */
    public static final String URL_LOGIN  = BASE_URL + "/login";



    /****************************  以下咸亨专用  *********************************/

    private static final String NAME = "url_save_area";
    private static final String KEY_IP = "key_ip";
    private static final String KEY_PORT = "key_port";
    private static URL url = new URL();
    private SharedPreferences preferences;

    public static URL operate(){
        return url;
    }

    public static String get(){
       return url.log().getUrl();
    }

    private URL(){
        preferences = Framework.get().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public String getUrl() {
        return "http://"+getIp() + ":" + getPort();
    }

    public String getIp() {
        String ipDefault = "192.168.0.1";
        return preferences.getString(KEY_IP, ipDefault);
    }

    public URL setIp(String ip) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IP,ip);
        editor.apply();
        return this;
    }

    public int getPort() {
        int portDefault = 8080;
        return preferences.getInt(KEY_PORT, portDefault);
    }

    public URL setPort(int port) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_PORT,port);
        editor.apply();
        return this;
    }

    public URL log(){
        LogUtil.d("URL_",""+getUrl());
        return this;
    }

    /**
     * 检测ip地址是否正确。
     */
    public static boolean checkIp(String ip) {
        if (ip.length() < 7 || ip.length() > 15) {
            return false;
        }
        String rule = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rule);
        Matcher mat = pat.matcher(ip);
        return mat.matches();
    }

    public static boolean checkPort(int port) {
        return port > 0 && port <= 65535;
    }
}
