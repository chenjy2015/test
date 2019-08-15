package com.example.chenjy.myapplication.base.fragment;

import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Create by Melorin on 2018/11/20
 */
public class BaseFragment extends Fragment implements LifecycleProvider<FragmentEvent> {


    private CompositeDisposable mCompositeDisposable;


    protected final BehaviorSubject<FragmentEvent> lifeSubject = BehaviorSubject.create();

    @Override
    public Observable<FragmentEvent> lifecycle() {
        return lifeSubject.hide();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifeSubject, event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifeSubject);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifeSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeSubject.onNext(FragmentEvent.START);
    }


    @Override
    public void onResume() {
        super.onResume();
        lifeSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifeSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifeSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroy() {
        lifeSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        clearDisposable();
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}