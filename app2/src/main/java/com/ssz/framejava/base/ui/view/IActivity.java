package com.ssz.framejava.base.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public interface IActivity {
    void beforeOnCrete(@Nullable Bundle savedInstanceState);
    int getLayoutId();
    void afterOnCreate(@Nullable Bundle savedInstanceState);
    BehaviorSubject<ActivityEvent> provideBehaviorSubject();
    void addDisposable(@Nullable Disposable d);
}
