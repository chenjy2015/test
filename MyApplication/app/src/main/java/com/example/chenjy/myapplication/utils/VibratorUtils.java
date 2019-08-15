package com.example.chenjy.myapplication.utils;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibratorUtils {

    public static boolean isVibrator;

    /**
     * final Activity activity ：调用该方法的Activity实例 long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
// 一直震动多少秒
    public static void vibrate(final Activity activity, long milliseconds) {
        isVibrator = true;
        Vibrator vib = (Vibrator) activity
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    // 按照我们传进去的数组进行间歇性的震动
    public static void vibrate(final Activity activity, long[] pattern,
                               boolean isRepeat) {
        isVibrator = true;
        Vibrator vib = (Vibrator) activity
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    // 停止震动
    public static void stopVibrate(final Activity activity) {
        isVibrator = false;
        Vibrator vib = (Vibrator) activity
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}