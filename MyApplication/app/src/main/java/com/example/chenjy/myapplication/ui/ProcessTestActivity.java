package com.example.chenjy.myapplication.ui;

import android.content.Intent;
import android.util.Log;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.services.ProcessTestService;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class ProcessTestActivity extends BaseUIActivity {

    public static boolean processFlag = false;
    public static final String TAG = "viclee";

    @Override
    public int getLayout() {
        return R.layout.activity_process_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        processFlag = true;
        Log.i(TAG, "ProcessTestActivity onCreate");
        this.startService(new Intent(this, ProcessTestService.class));
    }
}