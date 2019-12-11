package com.ssz.framejava.module;

import android.util.Log;

import com.ssz.framejava.model.remote.net.Api;

import javax.inject.Inject;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 16:40
 * dagger2
 */
public class FryDay {

    @Inject
    Api mApi;

    @Inject
    public FryDay(){

    }

    public void log(){
        Log.d("FryDay",""+mApi);
    }
}
