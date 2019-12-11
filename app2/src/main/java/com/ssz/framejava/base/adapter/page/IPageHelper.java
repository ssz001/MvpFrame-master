package com.ssz.framejava.base.adapter.page;

/**
 * @author : zsp
 * time : 2019 11 2019/11/11 9:00
 */
public interface IPageHelper {
    void nextPage();
    void lastPage();
    void jumpToPage(int pageNum);
    void reset();
}
