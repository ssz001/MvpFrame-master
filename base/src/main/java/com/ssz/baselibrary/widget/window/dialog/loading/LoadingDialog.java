package com.ssz.baselibrary.widget.window.dialog.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import com.ssz.baselibrary.R;
import com.ssz.baselibrary.widget.window.dialog.BaseDialog;
import com.ssz.baselibrary.widget.window.dialog.DialogConfig;

import org.jetbrains.annotations.NotNull;

public class LoadingDialog extends BaseDialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public @NotNull DialogConfig loadConfig() {
        return new DialogConfig.Builder()
                .view(R.layout.dialog_loading)
                .width(unit(300, TypedValue.COMPLEX_UNIT_DIP))
                .height(WRAP_CONTENT)
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    public void applyRootView(View rootView) {

    }
}
