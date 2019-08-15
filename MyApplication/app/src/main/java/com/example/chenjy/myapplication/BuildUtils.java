package com.example.chenjy.myapplication;

import android.os.Build;

/**
 * Created by chenjy on 2019/4/24.
 */

public class BuildUtils {
    public static boolean matchVersion() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    }
}
