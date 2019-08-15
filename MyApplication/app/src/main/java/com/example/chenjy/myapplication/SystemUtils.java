package com.example.chenjy.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by chenjy on 2019/4/4.
 */

public class SystemUtils {
    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    public static boolean isExistActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        ComponentName cmpName = intent.resolveActivity(context
                .getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context
                    .getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break; // 跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    /**
     * 判断 某个activity 是否存在
     *
     * @param context
     * @param packageName
     * @param className
     * @return
     */
    public static boolean isExistActivity(Context context, String packageName, String className) {
        Intent intent = new Intent();
//        intent.setClassName("包名", "类名");
        intent.setClassName(packageName, className);
        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
            // 说明系统中不存在这个activity
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断程序的运行在前台还是后台
     *
     * @param context
     * @return 0在后台运行  大于0在前台运行  2表示当前主界面是MainFragmentActivity
     */
    public static int isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = "com.xy.tencent";

        String bingMapMainActivityClassName = "com.xy.tencent.activity.MainFragmentActivity";
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            ComponentName topConponent = tasksInfo.get(0).topActivity;
            Log.d("TAG",
                    "topConponent.getPackageName()..."
                            + topConponent.getPackageName());
            if (packageName.equals(topConponent.getPackageName())) {
                // 当前的APP在前台运行
                if (topConponent.getClassName().equals(
                        bingMapMainActivityClassName)) {
                    // 当前正在运行的是不是期望的Activity
                    Log.d("TAG", "MainFragmentActivity在运行");
                    return 2;
                }
                Log.d("TAG", "com.xy.tencent前台运行");
                return 1;
            } else {
                // 当前的APP在后台运行
                Log.d("TAG", "com.xy.tencent后台运行");
                return 0;
            }
        }
        return 0;
    }
}
