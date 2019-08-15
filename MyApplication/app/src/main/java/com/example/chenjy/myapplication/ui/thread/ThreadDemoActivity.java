package com.example.chenjy.myapplication.ui.thread;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityThreadDemoBinding;
import com.jakewharton.rxbinding2.view.RxView;


public class ThreadDemoActivity extends BaseUIActivity<ActivityThreadDemoBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_thread_demo;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        addDisposable(
                RxView.clicks(mViewDataBinding.oneWay)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> startHandlerThread())
        );

        addDisposable(
                RxView.clicks(mViewDataBinding.twoWay)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> {})
        );
    }


    @Override
    public void initData() {


    }

    private void startHandlerThread(){
        HandlerThread handlerThread = new HandlerThread("handler thread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("当前子线程是----->", Thread.currentThread()+"");
            }
        };
        mHandler.sendEmptyMessage(1);
    }

    @SuppressLint("HandlerLeak")
    private void start(){
        myThread = new MyThread();
        myThread.start();
        mHandler = new Handler(myThread.looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("当前子线程是----->", Thread.currentThread()+"");
            }
        };
        mHandler.sendEmptyMessage(1);
    }

    private MyThread myThread;
    private Handler mHandler;

    class MyThread extends Thread{
        private Looper looper;
        @Override
        public void run() {
            super.run();
            Looper.prepare(); //创建子线程的Looper
            looper = Looper.myLooper();//取出该子线程的Looper
            Looper.loop();//只要调用了该方法才能不断循环取出消息
        }
    }


}
