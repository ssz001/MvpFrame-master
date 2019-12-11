package com.ssz.framejava.base.simple;


import com.ssz.framejava.T.SayBean;

import java.util.List;

/**
 * @author : zsp
 * time : 2019 11 2019/11/22 12:55
 */
public interface ISimpleView {
    void showProgress();

    void hideProgress();

    void jokeShow(List<SayBean> sayBeans);
}
