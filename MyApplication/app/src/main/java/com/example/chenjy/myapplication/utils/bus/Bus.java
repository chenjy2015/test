package com.example.chenjy.myapplication.utils.bus;


import androidx.annotation.NonNull;
import io.reactivex.Observable;

/**
 * Created by melorin on 2017/12/3.
 */
public class Bus {

    public static <T> Observable<T> register(@NonNull Object pageObj,
                                             @NonNull String eventKey,
                                             @NonNull Class<T> clazz) {
        return RxBus.inst.register(pageObj.getClass().getCanonicalName(), eventKey, clazz);
    }

    public static void unregister(@NonNull Object pageObj,
                                  @NonNull String eventKey) {
        RxBus.inst.unregister(pageObj.getClass().getCanonicalName(), eventKey);
    }

    public static void unregister(@NonNull Object pageObj) {
        RxBus.inst.unregister(pageObj.getClass().getCanonicalName());
    }

    public static void post(@NonNull String eventKey, @NonNull final Object content) {
        RxBus.inst.post(eventKey, content);
    }
}
