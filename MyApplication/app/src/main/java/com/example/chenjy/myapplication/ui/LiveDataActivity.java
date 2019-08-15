package com.example.chenjy.myapplication.ui;

import android.os.Handler;
import android.os.Looper;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityLiveDataBinding;
import com.example.chenjy.myapplication.livedata.NetworkLiveData;
import com.example.chenjy.myapplication.livedata.TestView2Model;
import com.example.chenjy.myapplication.livedata.TestViewModel;
import com.example.chenjy.myapplication.utils.L;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class LiveDataActivity extends BaseUIActivity<ActivityLiveDataBinding> {

    private static final String TAG = "LiveDataActivity >> NetworkLiveData";

    TestViewModel viewModel;
    TestView2Model view2Model;
    MutableLiveData<String> nameEvent;

    @Override
    public int getLayout() {
        return R.layout.activity_live_data;
    }

    @Override
    public void init() {
        super.init();
        viewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        view2Model = ViewModelProviders.of(this, new TestView2Model.Factory("this is a demo!")).get(TestView2Model.class);
        nameEvent = view2Model.getNameEvent();
        nameEvent.observe(this, s -> mViewDataBinding.title.setText(s));
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        NetworkLiveData.getInstance(this)
                .observe(this, networkInfo ->
                        L.d(TAG, "onChanged: networkInfo=" + networkInfo));
    }

    @Override
    public void initData() {
//        new Handler().postDelayed(()->nameEvent.setValue("this is a liveData demo!"),1000);
        change();
    }

    public void change() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Looper.prepare();
                new Handler().postDelayed(() -> nameEvent.postValue("this is a liveData demo!"), 1000);
//                new Handler().postDelayed(() -> nameEvent.setValue("this is a liveData demo!"), 1000);
                Looper.loop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
