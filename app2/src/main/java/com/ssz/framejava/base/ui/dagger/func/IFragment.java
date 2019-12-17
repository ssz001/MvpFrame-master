package com.ssz.framejava.base.ui.dagger.func;

import android.os.Bundle;

public interface IFragment {
    void beforeOnCreateView(Bundle savedInstanceState);
    void initInject();
    int getLayoutId();
    void afterOnCreateView(Bundle savedInstanceState);
}
