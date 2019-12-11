package com.ssz.framejava.base.adapter.func;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 15:05
 */
public interface IPageOperate {
    /**  下一页 */
    void nextPage(int pageNum);
    /**  上一页 */
    void lastPage(int pageNum);
    /**  跳转到第几页 */
    void jumpToPage(int pageNum);
}
