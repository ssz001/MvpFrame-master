package com.ssz.framejava.utils.log;

import android.support.annotation.NonNull;

import com.ssz.framejava.BuildConfig;

import timber.log.Timber;

/**
 * @author : zsp
 * time : 2019 11 2019/11/18 16:25
 */
public class TimberUtil {

    public static void init(){
        Timber.plant(new LogTree());
    }

    /****************** self Log *******************/
    static class LogTree extends Timber.DebugTree{

        @Override
        protected boolean isLoggable(String tag, int priority) {
//            return super.isLoggable(tag, priority);
            return BuildConfig.DEBUG;
        }

        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            super.log(priority, tag, message, t);
        }
    }
}
