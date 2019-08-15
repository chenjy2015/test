package com.example.chenjy.myapplication.ui;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityRetrofitDemoBinding;
import com.example.chenjy.myapplication.retrofit.Contacts;
import com.example.chenjy.myapplication.retrofit.NetWorkManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class RetrofitDemoActivity extends BaseUIActivity<ActivityRetrofitDemoBinding> {

    NetWorkManager netWorkManager;

    @Override
    public int getLayout() {
        return R.layout.activity_retrofit_demo;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        addDisposable(
                RxView.clicks(mViewDataBinding.get)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> netWorkManager.getBook(1010))
        );

    }

    @Override
    public void initData() {
        netWorkManager = NetWorkManager.getInstance().init(this, Contacts.HOME);
    }

}
