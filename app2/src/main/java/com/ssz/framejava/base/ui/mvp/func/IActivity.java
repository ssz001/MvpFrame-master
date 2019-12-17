package com.ssz.framejava.base.ui.mvp.func;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface IActivity {
    void beforeOnCrete(Bundle savedInstanceState);
    int getLayoutId();
    void afterOnCreate(@Nullable Bundle savedInstanceState);
}
