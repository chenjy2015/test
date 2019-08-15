package com.example.chenjy.myapplication.permission;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by melorin on 2017/3/14.
 */

public class Utils {

    private static Context context;

    public static void init(Context context) {
        Utils.context = context;
    }

    public static <T> T getSystemService(@NonNull String name) {
        return (T) context.getSystemService(name);
    }

    public static String getString(@StringRes int resId) {
        return context.getResources().getString(resId);
    }

//    public static Drawable getDrawable(@DrawableRes int resId) {
//        return context.getDrawable(resId);
//    }

    @ColorInt
    public static int getColor(@ColorRes int colorId) {
        return context.getResources().getColor(colorId);
    }

    public static Context getContext() {
        return context;
    }
}
