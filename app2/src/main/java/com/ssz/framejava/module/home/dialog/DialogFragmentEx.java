package com.ssz.framejava.module.home.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.widget.window.dialog.BaseDialog;
import com.ssz.framejava.widget.window.dialog.DialogConfig;

import org.jetbrains.annotations.NotNull;

public class DialogFragmentEx extends BaseDialog {


    public DialogFragmentEx(@NonNull Context context) {
        super(context);
    }

    @Override
    public @NotNull DialogConfig loadConfig() {
        return new DialogConfig.Builder()
                .view(R.layout.dialog_loading_hor)
                .canceledOnTouchOutside(false)
                .width(unit(300, TypedValue.COMPLEX_UNIT_DIP))
                .height(WRAP_CONTENT)
                .build();
    }

    @Override
    public void applyRootView(View rootView) {

    }

}
