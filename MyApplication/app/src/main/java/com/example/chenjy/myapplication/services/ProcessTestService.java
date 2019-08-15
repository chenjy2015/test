package com.example.chenjy.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.chenjy.myapplication.ui.ProcessTestActivity;

public class ProcessTestService extends Service {

    public static final String TAG = "viclee";

    @Override
    public void onCreate() {
        Log.i(TAG, "ProcessTestService onCreate");
        Log.i(TAG, "ProcessTestActivity.processFlag is " + ProcessTestActivity.processFlag);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
