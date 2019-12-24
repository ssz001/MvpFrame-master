/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssz.baselibrary.utils;

import android.support.annotation.CheckResult;

import com.ssz.baselibrary.view.ui.IActivity;
import com.ssz.baselibrary.view.ui.IFragment;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public final class RxLifecycleUtil {

    private RxLifecycleUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle(IActivity view) {
        if (ObjectHelper.isNull(view))
            throw new IllegalStateException("please useRxLifecycle() return = true");
        return view.provideBehaviorSubject().hide();
    }

    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle(IFragment view) {
        if (ObjectHelper.isNull(view))
            throw new IllegalStateException("please useRxLifecycle() return = true");
        return view.provideBehaviorSubject().hide();
    }

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IActivity view,
                                                             final ActivityEvent event) {
        ObjectHelper.checkNotNull(view, "view == null");
        return RxLifecycle.bindUntilEvent(ObjectHelper.requireNotNull(view.provideBehaviorSubject()),event);
    }

    /**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IFragment view,
                                                             final FragmentEvent event) {
        ObjectHelper.checkNotNull(view, "view == null");
        return RxLifecycle.bindUntilEvent(ObjectHelper.requireNotNull(view.provideBehaviorSubject()),event);
    }

    /**
     * bindToLifecycle()
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Object view) {
        ObjectHelper.checkNotNull(view, "view == null");
        if (view instanceof IActivity) {
            return RxLifecycleAndroid.bindActivity(((IActivity) view).provideBehaviorSubject());
        } else if (view instanceof IFragment) {
            return RxLifecycleAndroid.bindFragment(((IFragment)view).provideBehaviorSubject());
        } else {
            throw new IllegalArgumentException("view not match");
        }
    }
}
