package com.ssz.framejava.base.ui.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.utils.toast.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : zsp
 * time : 2019 11 2019/11/22 12:47
 */
public class SimpleActivity extends AppCompatActivity implements ISimpleView {

    SimplePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);
        mPresenter = new SimplePresenter(this, new SimpleInteractor());
    }

    @OnClick({R.id.bt_get_joke, R.id.bt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get_joke:
                mPresenter.getJoke(1, 2, "video");
                break;
            case R.id.bt_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {
        ToastUtil.showToast(this, "获取数据中...");
    }

    @Override
    public void hideProgress() {
        ToastUtil.showToast(this, "获取数据成功");
    }

    @Override
    public void jokeShow(List<SayBean> sayBeans) {
        // xxAdapter.refreshData(sayBeans);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
