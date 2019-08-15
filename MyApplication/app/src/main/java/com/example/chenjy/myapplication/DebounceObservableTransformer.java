package com.example.chenjy.myapplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * @route:
 * @descript:防止快速点击
 * @create: chenjiayou
 * create at 2018/11/21 9:35
 */
public class DebounceObservableTransformer<T> implements ObservableTransformer<T, T> {

    private static final long DEBOUNCE = 500; //500毫秒间隔时间

    private final long interval;

    public DebounceObservableTransformer() {
        this(DEBOUNCE);
    }

    public DebounceObservableTransformer(long interval) {
        this.interval = interval;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.throttleFirst(this.interval, TimeUnit.MILLISECONDS);
    }
}
