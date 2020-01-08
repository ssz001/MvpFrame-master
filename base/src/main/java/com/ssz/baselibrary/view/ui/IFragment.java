package com.ssz.baselibrary.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public interface IFragment {
    void beforeOnCreateView(@Nullable Bundle savedInstanceState);
    int getLayoutId();
    void afterOnCreateView(@Nullable Bundle savedInstanceState);
    BehaviorSubject<FragmentEvent> provideBehaviorSubject();
    void addDisposable(@Nullable Disposable d);
}
