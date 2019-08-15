package com.example.chenjy.myapplication.utils.bus;


import com.blankj.utilcode.util.ObjectUtils;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

class RxBus {

    static final RxBus inst = Holder.holder;
    private Hashtable<String, Map<String, Subject>> mSubjects = new Hashtable<>();

    private RxBus() {
    }

    <T> Observable<T> register(@NonNull String pageKey,
                               @NonNull String eventKey,
                               @NonNull Class<T> clazz) {
        Map<String, Subject> map = mSubjects.get(pageKey);
        if (null == map) {
            map = new HashMap<>();
            mSubjects.put(pageKey, map);
        }
        Subject<T> subject = map.get(eventKey);
        if (null == subject) {
            subject = PublishSubject.create();
            map.put(eventKey, subject);
        }
        return subject;
    }

    void unregister(@NonNull String pageKey,
                    @NonNull String eventKey) {
        Map<String, Subject> map = mSubjects.get(pageKey);
        if (map != null && !map.isEmpty()) {
            map.remove(eventKey);
        }
    }

    void unregister(@NonNull String pageKey) {
        mSubjects.remove(pageKey);
    }

    void post(@NonNull final String eventKey, @NonNull final Object content) {
        Set<String> pageKeySet = mSubjects.keySet();
        for (String pageKey : pageKeySet) {
            Map<String, Subject> map = mSubjects.get(pageKey);
            if (!ObjectUtils.isEmpty(map)) {
                Subject subject = map.get(eventKey);
                if (!ObjectUtils.isEmpty(subject)) {
                    subject.onNext(content);
                }
            }
        }

//        Observable.fromIterable(mSubjects.entrySet())
//                .map(Map.Entry::getValue)
//                .filter(ValidUtils::isValid)
//                .map(objectSubjectMap -> objectSubjectMap.get(eventKey))
//                .filter(ValidUtils::isValid)
//                .subscribe(subject -> subject.onNext(content), Throwable::printStackTrace);
    }

    private static final class Holder {
        private static final RxBus holder = new RxBus();
    }
}