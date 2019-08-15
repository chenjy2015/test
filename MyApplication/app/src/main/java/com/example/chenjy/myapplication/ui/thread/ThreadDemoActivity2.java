package com.example.chenjy.myapplication.ui.thread;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityThreadDemoBinding;
import com.jakewharton.rxbinding2.view.RxView;


public class ThreadDemoActivity2 extends BaseUIActivity<ActivityThreadDemoBinding> {

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
                        .subscribe(o -> oneWayHandlerThread())
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Handler handler;
    private String TAG = "ThreadDemoActivity";

    /**
     * 单向通信发消息
     */
    private void oneWayHandlerThread() {
        Thread hanMeiMeiThread = new Thread() {
            @SuppressLint("HandlerLeak")
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, "hanMeiMei receiver message: " + ((String) msg.obj));
                        Toast.makeText(ThreadDemoActivity2.this, ((String) msg.obj), Toast.LENGTH_SHORT).show();
                    }
                };
                Looper.loop();
            }
        };

        Thread liuXiThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = Message.obtain();
                message.obj = "Hi HanMeiMei";
                handler.sendMessage(message);
            }
        };

        hanMeiMeiThread.setName("韩梅梅 Thread");
        hanMeiMeiThread.start();
        liuXiThread.setName("刘喜 Thread");
        liuXiThread.start();
    }


}
