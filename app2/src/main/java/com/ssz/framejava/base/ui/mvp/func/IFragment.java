package com.ssz.framejava.base.ui.mvp.func;

import android.os.Bundle;

public interface IFragment {
    void beforeOnCreateView(Bundle savedInstanceState);
    int getLayoutId();
    void afterOnCreateView(Bundle savedInstanceState);
}
