package com.ssz.framejava.base.ui.view;

import android.os.Bundle;

import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.subjects.BehaviorSubject;

public interface IFragment {
    void beforeOnCreateView(Bundle savedInstanceState);
    int getLayoutId();
    void afterOnCreateView(Bundle savedInstanceState);
    BehaviorSubject<FragmentEvent> provideBehaviorSubject();
}
