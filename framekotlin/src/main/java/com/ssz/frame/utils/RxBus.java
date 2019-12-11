package com.ssz.frame.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * 加入背压处理
 * Rxbus
 */
public class RxBus {

    private final FlowableProcessor<Object> _bus;

    private RxBus() {
        // toSerialized method made bus thread safe
        _bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.BUS;
    }

    public void send(Object obj) {
        _bus.onNext(obj);
    }
    /**
     * 根据传递的 eventType 类型返回特定事件类型的被观察者
     */
    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return _bus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return _bus;
    }

    public boolean hasSubscribers() {
        return _bus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    /**
     *      使用方法
     *
     *      接收
     * RxBus.getInstance().toFlowable().subscribe(new Consumer<Object>(){
     *     @Override
     *      public void accept(Object o)throws Exception{
     *      if(((Event)o).getMsg()!=null){
     *           mTvReceive.setText(((TestEvent)o).getMsg());
     *       }
     *     }
     *  });
     *
     *     发送
     * mBtnReturn.setOnClickListener(new View.OnClickListener() {
     *     @Override
     *    public void onClick(View view) {
     *           RxBus.getInstance().send(new Event(mEtNext.getText().toString()));
     *         finish();
     *      }
     * });
     */
}
