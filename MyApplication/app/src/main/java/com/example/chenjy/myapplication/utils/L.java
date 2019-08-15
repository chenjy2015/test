package com.example.chenjy.myapplication.utils;

import com.blankj.utilcode.util.LogUtils;
import com.example.chenjy.myapplication.action.IAction1;
import com.google.gson.Gson;

/**
 * 日志打印类
 * <p>
 * Created by melorin on 2017/1/21.
 */
public class L {

    public static final String TAG = "DEBUG";
    public static final String HOLDER = "HOLDER";
    public static final String TEST = "BGY_REBUILD_TEST";

    public static boolean isDebug = true;
    public static boolean withStackInfo = true;

    private static IAction1<String> mLogAction;

    public static void init(IAction1<String> afterAction) {
        mLogAction = afterAction;
    }

    public static void test(int result, String desc) {
        if (!isDebug) {
            return;
        }
        String log = getStackInfo() + String.format("print:\nresult: %s\ndesc: %s\n", result, desc);
        LogUtils.e(TEST, log);
        notifyAction(log);
    }

    public static void test(int result, String desc, Object msg) {
        if (!isDebug) {
            return;
        }
        String log = getStackInfo() + String.format("print:\nresult: %s\ndesc: %s\nmessage:\n%s", result, desc, msg);
        LogUtils.e(TEST, log);
        notifyAction(log);
    }

    public static void test(Object msg) {
        if (!isDebug) {
            return;
        }
        String log = getStackInfo() + (msg != null ? msg.toString() : "null");
        LogUtils.e(TEST, log);
        notifyAction(log);
    }

    public static void test(String format, Object... args) {
        if (!isDebug) {
            return;
        }
        String log = getStackInfo() + String.format(format, args);
        LogUtils.e(TEST, log);
        notifyAction(log);
    }

    private static void notifyAction(String log) {
        if (mLogAction != null) {
            mLogAction.invoke(log);
        }
    }

    public static void i(String tag, Object msg) {
        if (msg != null) {
            LogUtils.i(tag, getStackInfo() + msg.toString());
        } else {
            LogUtils.i(tag, getStackInfo() + "null");
        }
    }

    public static void d(Object tag, Object msg) {
        if (msg != null) {
            LogUtils.d(tag.getClass().getSimpleName(), getStackInfo() + msg.toString());
        } else {
            LogUtils.d(tag.getClass().getSimpleName(), "null");
        }
    }

    public static void d(String tag, Object msg) {
        if (msg != null) {
            LogUtils.d(tag, getStackInfo() + msg.toString());
        } else {
            LogUtils.d(tag, "null");
        }
    }

    public static void e(Object msg) {
        if (msg != null) {
            LogUtils.e(TEST, getStackInfo() + msg.toString());
        } else {
            LogUtils.e(TEST, getStackInfo() + "null");
        }
    }

    public static void e(String tag, Object msg) {
        if (msg != null) {
            LogUtils.e(tag, getStackInfo() + msg.toString());
        } else {
            LogUtils.e(tag, "null");
        }
    }

    public static void ej(Object obj) {
        String msg = new Gson().toJson(obj);
        if (msg != null) {
            LogUtils.e("TEST", getStackInfo() + msg);
        } else {
            LogUtils.e("TEST", "null");
        }
    }

    public static void eF(Object obj) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
        if (elements != null) {
            for (StackTraceElement element : elements) {
                String[] struct = element.getClassName().split("\\.");
                String className = struct[struct.length - 1];
                className = className.split("\\$")[0];
                sb.append(String.format("%s (%s.java:%s)\n", element.getClassName(), className, element.getLineNumber()));
            }
        }
        if (obj == null) {
            sb.append("null");
        } else {
            sb.append(obj.getClass().getSimpleName());
            sb.append("    ");
            sb.append(new Gson().toJson(obj));
        }
        LogUtils.e(TEST, sb.toString());
    }

    private static String getStackInfo() {
        return getStackInfo(6);
    }

    private static String getStackInfo(int lines) {
        StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
        String info = "";
        if (!withStackInfo) {
            return info;
        }

        if (elements != null && lines < elements.length) {
            StackTraceElement element = elements[lines];
            String[] struct = element.getClassName().split("\\.");
            String className = struct[struct.length - 1];
            className = className.split("\\$")[0];
            info += String.format("%s (%s.java:%s)\n", element.getClassName(), className, element.getLineNumber());
        }
        return info;
    }
}
