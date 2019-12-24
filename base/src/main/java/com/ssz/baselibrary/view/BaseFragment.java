package com.ssz.baselibrary.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ssz.baselibrary.utils.ObjectHelper;
import com.ssz.baselibrary.utils.toast.ToastUtil;


/**
 * @author : zsp
 * time : 2019 11 2019/11/4 9:00
 */
public abstract class BaseFragment extends Fragment {

    protected String getText(final TextView view) {
        if (ObjectHelper.nonNull(view)) {
            return view.getText().toString();
        }
        return "";
    }

    protected void dismiss(final Dialog dialog) {
        if (ObjectHelper.nonNull(dialog) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void dismiss(final PopupWindow popupWindow) {
        if (ObjectHelper.nonNull(popupWindow) && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    protected void showToast(final String msg) {
        ToastUtil.showToast(getActivity(), msg);
    }

    protected void showToast(final String msg, int gravity) {
        ToastUtil.showToast(getActivity(), msg, gravity);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(getActivity(), clz), requestCode);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}
