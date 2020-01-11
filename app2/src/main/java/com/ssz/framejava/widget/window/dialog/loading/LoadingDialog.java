package com.ssz.framejava.widget.window.dialog.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import com.ssz.framejava.widget.window.dialog.BaseDialog;
import com.ssz.framejava.widget.window.dialog.DialogConfig;

import org.jetbrains.annotations.NotNull;

public class LoadingDialog extends BaseDialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public @NotNull DialogConfig loadConfig() {
        return new DialogConfig.Builder()
                .width(unit(100, TypedValue.COMPLEX_UNIT_DIP))
                .height(WRAP_CONTENT)
                .cancelAble(true)
                .canceledOnTouchOutside(true)
                .build();
    }

    @Override
    public void applyRootView(View rootView) {

    }
}
