package com.ssz.framejava.widget.window.dialog.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.widget.window.dialog.BaseDialog;
import com.ssz.framejava.widget.window.dialog.DialogConfig;

import org.jetbrains.annotations.NotNull;

/**
 * @author : zsp
 * time : 2020 01 2020/1/13 9:01
 */
public class ProgressDialog extends BaseDialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public @NotNull DialogConfig loadConfig() {
        return new DialogConfig.Builder()
                .view(R.layout.dialog_loading_hor)
                .width(unit(300, TypedValue.COMPLEX_UNIT_DIP))
                .height(WRAP_CONTENT)
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    public void applyRootView(View rootView) {

    }
}
