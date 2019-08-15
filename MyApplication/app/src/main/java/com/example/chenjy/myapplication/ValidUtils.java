package com.example.chenjy.myapplication;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by melorin on 2017/12/3.
 */
public class ValidUtils {

    public static boolean isValid(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isValid(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isValid(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean isValid(Object obj) {
        return obj != null;
    }
}
