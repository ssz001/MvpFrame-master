package com.ssz.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.utils.ObjectHelper;
import com.ssz.baselibrary.utils.toast.ToastUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;

/**
 * @author : zsp
 * time : 2019 12 2019/12/30 16:19
 */
public abstract class BaseDialog extends Dialog {

    public final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;

    protected View rootView;
    private DialogConfig mConfig;

    public BaseDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context){
        mConfig = ObjectHelper.requireNotNull(loadConfig(), "config == null");
        rootView = LayoutInflater.from(context).inflate(mConfig.viewId, null);
        ButterKnife.bind(this, rootView);
        applyRootView(rootView);
    }

    @NotNull
    public abstract DialogConfig loadConfig();

    public abstract void applyRootView(View rootView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootView);
        // setCanceledOnTouchOutside 优先级高
        setCancelable(mConfig.cancelAble);
        setCanceledOnTouchOutside(mConfig.canceledOnTouchOutside);

        Window win = getWindow();
        WindowManager.LayoutParams lp;
        if (win != null) {
            lp = win.getAttributes();
            lp.gravity = mConfig.gravity;
            lp.height = mConfig.height;
            lp.width = mConfig.width;
            win.setAttributes(lp);
        }
    }

    public int unit(int size, int typeValue) {
        return (int) (TypedValue.applyDimension(typeValue, size,
                AppHelper.getApplication().getResources().getDisplayMetrics()));
    }

    /*************************** get resources *************************/

    public int getColorById(int res){
        return ContextCompat.getColor(getContext(),res);
    }

    public Drawable getDrawableById(int res){
        return ContextCompat.getDrawable(getContext(),res);
    }

    public int getDimenById(int dimenId){
        return (int)getContext().getResources().getDimension(dimenId);
    }

    public float getDimenFloatById(int dimenId){
        return getContext().getResources().getDimension(dimenId);
    }

    public String getStringById(int resId){
        return getContext().getResources().getString(resId);
    }

    /*************************** get resources end *************************/

    protected void showToast(final String msg) {
        ToastUtil.showToast(getContext(), msg);
    }

    protected void showToast(final String msg, int gravity) {
        ToastUtil.showToast(getContext(), msg, gravity);
    }
}
