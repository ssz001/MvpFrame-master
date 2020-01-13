package com.ssz.framejava.widget.window.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.ui.dagger.func.DaggerPresenter;
import com.ssz.framejava.utils.ObjectHelper;
import com.ssz.framejava.utils.toast.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author : zsp
 * time : 2019 12 2019/12/30 16:19
 * 因为和Fragment有关，不要复用d情况下使用，不适合用于loadingDialog
 */
public abstract class BaseDialogFragment extends DialogFragment {

    public final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
    public final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;

    protected CompositeDisposable mcDisposable;
    private BehaviorSubject<FragmentEvent> mLifecycleSubject;
    private Unbinder mBinder;

    private DialogConfig mConfig;
    protected View rootView;

    /**
     * 默认不使用RxLifecycle
     */
    protected boolean useRxLifecycle(){
        return false;
    }

    @NotNull
    public abstract DialogConfig loadConfig();

    public abstract void applyRootView(View rootView);

//    public abstract void setStyle();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mConfig = loadConfig();
        if (useRxLifecycle()){
            mLifecycleSubject = BehaviorSubject.create();
        }
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.CREATE);
//        setStyle();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(mConfig.viewId,container,false);
        mBinder = ButterKnife.bind(this,rootView);
        applyRootView(rootView);
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // setCanceledOnTouchOutside 优先级高
        Dialog dialog = getDialog();
        dialog.setCancelable(mConfig.cancelAble);
        dialog.setCanceledOnTouchOutside(mConfig.canceledOnTouchOutside);
        Window win = dialog.getWindow();
        if (win != null) {
            WindowManager.LayoutParams lp;
            lp = win.getAttributes();
            lp.gravity = mConfig.gravity;
            lp.height = mConfig.height;
            lp.width = mConfig.width;
            lp.dimAmount = mConfig.dimAmount;
            lp.horizontalMargin = mConfig.horizonMargin;
            lp.verticalMargin = mConfig.verticalMargin;
            lp.x = mConfig.offsetX;
            lp.y = mConfig.offsetY;
            lp.alpha = mConfig.alpha;
            lp.windowAnimations = mConfig.animation;
            win.setAttributes(lp);
        }
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.START);
        // WindowManager.LayoutParams attributes = window.getAttributes();
        //        //设置Dialog窗口的高度
        //        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        //        //设置Dialog窗口的宽度
        //        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        //        //设置Dialog的居中方向
        //        attributes.gravity = Gravity.CENTER;
        //        //设置Dialog弹出时背景的透明度
        //        attributes.dimAmount = 0.6f;
        //        //设置Dialog水平方向的间距
        //        attributes.horizontalMargin = 0f;
        //        //设置Dialog垂直方向的间距
        //        attributes.verticalMargin = 0f;
        //        //设置Dialog显示时X轴的坐标,具体屏幕X轴的偏移量
        //        attributes.x = 0;
        //        //设置Dialog显示时Y轴的坐标,距离屏幕Y轴的偏移量
        //        attributes.y = 0;
        //        //设置Dialog的透明度
        //        attributes.alpha = 0f;
        //        //设置Dialog显示和消失时的动画
        //        attributes.windowAnimations = 0;
        //        window.setAttributes(attributes);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
//        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        if (ObjectHelper.nonNull(mBinder))mBinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DETACH);
        this.mBinder = null;
//        this.mPresenter = null;
        super.onDetach();
    }


    public void addDisposable(@Nullable Disposable d) {
        if (ObjectHelper.isNull(d)) return;
        if (d.isDisposed())return;
        if (ObjectHelper.isNull(mcDisposable)) {
            mcDisposable = new CompositeDisposable();
        }
        mcDisposable.add(d);
    }

    protected void removeDisposable(@Nullable Disposable d) {
        if (ObjectHelper.isNull(d)) return;
        if (ObjectHelper.nonNull(mcDisposable)) {
            boolean remove = mcDisposable.remove(d);
            if (!remove) d.dispose();
        } else {
            d.dispose();
        }
    }

    public int unit(int size, int typeValue) {
        return (int) (TypedValue.applyDimension(typeValue, size,
               getApplication().getResources().getDisplayMetrics()));
    }


    /*************************** get resources *************************/

    public int getColorById(int res){
        return ContextCompat.getColor(getApplication(),res);
    }

    public Drawable getDrawableById(int res){
        return ContextCompat.getDrawable(getApplication(),res);
    }

    public int getDimenById(int dimenId){
        return (int)getApplication().getResources().getDimension(dimenId);
    }

    public float getDimenFloatById(int dimenId){
        return getApplication().getResources().getDimension(dimenId);
    }

    public String getStringById(int resId){
        return getApplication().getResources().getString(resId);
    }

    /*************************** get resources end *************************/

    protected void showToast(final String msg) {
        ToastUtil.showToast(getApplication(), msg);
    }

    protected void showToast(final String msg, int gravity) {
        ToastUtil.showToast(getApplication(), msg, gravity);
    }

    @NotNull
    private Context getApplication(){
        return AppHelper.getAppContext();
    }


}
