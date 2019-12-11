package com.ssz.studydemo.utils.network;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import com.ssz.studydemo.utils.log.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

/**
 * @author : zsp
 * time : 2019 10 2019/10/11 8:54
 */
public final class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    private static final String ANDROID_NET_CHANGE_ACTION = "android_net_change_action";

//  step:1
    //    <receiver android:name="x">
    //       <intent-filter>
    //             <action android:name="android.net.conn.CONNECTIVITY_CHANGE"></action>
    //      </intent-filter>
    //    </receiver>

    //    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    //    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
    //    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
    //    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>

    private static volatile NetworkManager instance;
    private NetStateReceiver mReceiver;
    private Application mApplication;
    private final HashMap<Object, MethodManager> mNetworkList = new HashMap(16);

    public NetworkManager() {
        mReceiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param register
     */
    public void registerNetWorkObserver(Object register) {
        //获取当前Activity or Fragment中所有的网络监听注解方法
       MethodManager mMethod = mNetworkList.get(register);
        //说明没注册过
        if (mMethod == null) {
            mMethod = findAnnotationMethod(register);
            mNetworkList.put(register, mMethod);
        }
    }

    /**
     * @param register
     * @return MethodList 网络监听注解方法数组
     */
    private MethodManager findAnnotationMethod(Object register) {
        MethodManager methodManager = null;
        Class<?> clazz = register.getClass();
        // public
        Method[] method = clazz.getMethods();
        //遍历方法
        for (Method m : method) {
            //找出唯一的一个注解方法
            NetworkListener annotation = m.getAnnotation(NetworkListener.class);
            if (annotation == null) {
                continue;
            }
            //判断返回类型
            Type genericReturnType = m.getGenericReturnType();
            if (!"void".equals(genericReturnType.toString())) {
                throw new RuntimeException(m.getName() + "返回类型必须是void");
            }

            //参数校验
            Class<?>[] parameterTypes = m.getParameterTypes();
            Log.i("m,name", m.getParameterTypes().length + "");
            if (parameterTypes.length != 1) {
                // public void netorkListen(@NetType String type){}
                throw new RuntimeException(m.getName() + "返回参数只有一个");
            }
            methodManager = new MethodManager(parameterTypes[0], annotation.type(), m);
            // 只允许一个方法
            break;
        }
        return methodManager;
    }

    /**
     * 注销
     *
     * @param register
     */
    public void unRegisterNetWorkObserver(Object register) {
        //说明有广播被注册过
        if (!mNetworkList.isEmpty()) {
            mNetworkList.remove(register);
        }
        Log.i(TAG, register.getClass().getName() + "注销成功了");
    }

    public void unRegisterAllNetWorkObserver() {
        //说明有广播被注册过
        if (!mNetworkList.isEmpty()) {
            mNetworkList.clear();
        }
        // 调用这个方法默认是getDefault()
        NetworkManager.getDefault().logout();//注销
    }

    private class NetStateReceiver extends BroadcastReceiver {

        private @NetType
        String type;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                Log.e(TAG, "广播异常了");
                return;
            }
            if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
                Log.e(TAG, "网络状态变化了");
                type = NetWorkUtil.getNetworkType(getApplication());
                dispatch(type); //分发
            }
        }
    }

    /**
     * @param netType 优先级：CONNECT(有网),MOBILE()
     */
    private void dispatch(@NetType String netType) {
        final Set<Object> set = mNetworkList.keySet();
        for (Object o : set) {
            final MethodManager methodManager = mNetworkList.get(o);
            if (null == methodManager) return;
            for (@NetType String netTypeApply : methodManager.getNetType()) {
                if (netTypeApply == NetType.CONNECT) {
                    if (netType == NetType.MOBILE_2G
                            || netType == NetType.MOBILE_3G
                            || netType == NetType.MOBILE_4G
                            || netType == NetType.MOBILE_5G
                            || netType == NetType.WIFI) {
                        invoke(methodManager, o, netTypeApply);
                    }
                } else if (netTypeApply == NetType.MOBILE) {
                    if (netType == NetType.MOBILE_2G
                            || netType == NetType.MOBILE_3G
                            || netType == NetType.MOBILE_4G
                            || netType == NetType.MOBILE_5G) {
                        invoke(methodManager, o, netTypeApply);
                    }
                } else {
                    if (netType == netTypeApply) {
                        invoke(methodManager, o, netTypeApply);
                    }
                }
            }
//            for (MethodManager manager : methodManager) {
//                //如果注解上的参数和网络状态参数类型相同
//                if (manager.getType().isAssignableFrom(netType.getClass())) {
//                    switch (manager.getNetType()) {
//                        case NetType.CONNECT:
//                            //反射运行方法
//                            invoke(manager, o, netType);
//                            break;
//                        case NetType.MOBILE:
//                            // 可以用等于，因为传入是一样的
//                            if (netType == NetType.MOBILE || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                            break;
//                        case NetType.MOBILE_2G:
//                            if (netType == NetType.MOBILE_2G || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                            break;
//                        case NetType.MOBILE_3G:
//                            if (netType == NetType.MOBILE_3G || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                            break;
//                        case NetType.MOBILE_4G:
//                            if (netType == NetType.MOBILE_4G || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                            break;
//                        case NetType.MOBILE_5G:
//                            if (netType == NetType.MOBILE_5G || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                            break;
//                        case NetType.WIFI:
//                            if (netType == NetType.WIFI || netType == NetType.NONE) {
//                                invoke(manager, o, netType);
//                            }
//                        case NetType.NONE:
//                            invoke(manager, o, netType);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
        }
    }

    /**
     * 初始化网络状态监听,区分不同的Android版本
     */
    public void initNetworkCallBack() {
        ConnectivityManager manager = (ConnectivityManager) getApplication().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //API 大于26时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LogUtils.INSTANCE.d("dispatch", "api > 26");
            manager.registerDefaultNetworkCallback(mCallBack);
            //API 大于21时
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            LogUtils.INSTANCE.d("dispatch", "api > 21");
            manager.registerNetworkCallback(request, mCallBack);
        } else {
            //低版本
            LogUtils.INSTANCE.d("dispatch", "api < 21");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ANDROID_NET_CHANGE_ACTION);
            mApplication.registerReceiver(mReceiver, intentFilter);
        }
    }

    @SuppressLint("NewApi")
    private final ConnectivityManager.NetworkCallback mCallBack = new ConnectivityManager.NetworkCallback() {
        /**
         * 网络可用的回调连接成功
         */
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            @NetType String netType = NetWorkUtil.getNetworkType(getApplication());
            LogUtils.INSTANCE.d("dispatch", "NetType on = " + netType);
            dispatch(netType);
        }

        /**
         * 在网络连接正常的情况下，丢失数据会有回调 即将断开时
         */
        @Override
        public void onLosing(Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
        }

        /**
         * 网络不可用时调用和onAvailable成对出现
         */
        @Override
        public void onLost(Network network) {
            super.onLost(network);
            dispatch(NetType.NONE);
            LogUtils.INSTANCE.d("dispatch", "NetType = " + "NONE");
        }

        /**
         * 网络缺失network时调用
         */
        @Override
        public void onUnavailable() {
            super.onUnavailable();
        }

        /**
         * 网络功能更改 满足需求时调用
         * @param network
         * @param networkCapabilities
         */
        @Override
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
        }

        /**
         * 网络连接属性修改时调用
         * @param network
         * @param linkProperties
         */
        @Override
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
        }
    };

    /**
     * @param manager 方法管理类
     * @param o       方法所有者（activity/Fragment）
     * @param netType 网络类型参数
     */
    private void invoke(MethodManager manager, Object o, String netType) {
        Method executeMethod = manager.getMethod();
        try {
            executeMethod.invoke(o, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Application getApplication() {
        if (mApplication == null) {
            throw new RuntimeException("NetworkManager.getDefault().init()没有初始化");
        }
        return mApplication;
    }

    /**
     * 初始化
     */
    public void init(Application application) {
        this.mApplication = application;
        initNetworkCallBack();
    }

    public void logout() {
        getApplication().unregisterReceiver(mReceiver);
    }
}
