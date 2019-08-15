package com.example.chenjy.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.chenjy.myapplication.MyCrashHandler;
import com.example.chenjy.myapplication.permission.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CXApplication extends Application {

    public static final String TAG = "viclee";
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();


        String processName = getProcessName();
        //判断进程名，保证只有主进程运行
//        if (!TextUtils.isEmpty(processName) &&processName.equals(this.getPackageName())) {
            //在这里进行主进程初始化逻辑操作
            context = this;
            Utils.init(this);
            new Thread(() -> {
               MyCrashHandler crashHandler = new MyCrashHandler();
               Thread.setDefaultUncaughtExceptionHandler(crashHandler);
            });
            int pid = android.os.Process.myPid();
            Log.d(TAG, "MyApplication onCreate");
            Log.d(TAG, "MyApplication pid is " + pid);
//        }

    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
