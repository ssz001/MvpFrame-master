package com.ssz.studydemo.view.read

import com.ssz.frame.mvp.BasePresenter
import com.ssz.frame.mvp.BaseView

/**
 * @author : zsp
 * time : 2019 09 2019/9/20 9:34
 */
interface IReadContract {

    interface IReadView : BaseView<IReadPresenter>{

    }

    interface IReadPresenter : BasePresenter{

    }
}