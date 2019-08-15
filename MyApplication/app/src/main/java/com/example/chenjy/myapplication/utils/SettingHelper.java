package com.example.chenjy.myapplication.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.chenjy.myapplication.ui.MainActivity;


public class SettingHelper {
    public static final String FILE_SESSION_BG_NAME = "session_bg.jpg";
    public static final int OPEN_DEFAULT_REQUEST_CODE = 100; //打开默认图片列表
    public static final int OPEN_CAMERA_REQUEST_CODE = 101; //打开相机 拍照
    public static final int OPEN_ALBUM_REQUEST_CODE = 102; //打开相册 选中

    public void restartIntentApp(Activity act) {
        Intent intent = new Intent(act, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        act.finish();
        act.startActivity(intent);
    }
}
