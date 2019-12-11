package com.ssz.framejava.base.adapter.select;

/**
 * @author : zsp
 * time : 2019 11 2019/11/8 16:18
 * 实现此接口的Bean 必须 自己实现成员变量(transient)，和 不包含 该成员变量 equals()
 */
public interface ISelectBean {
    void setCheck(boolean isCheck);
    boolean isCheck();
}
