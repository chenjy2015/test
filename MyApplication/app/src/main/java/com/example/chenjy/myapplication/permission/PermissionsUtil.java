package com.example.chenjy.myapplication.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.chenjy.myapplication.ActivityCollector;
import com.example.chenjy.myapplication.utils.L;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * 运行时权限申请工具类
 * <p>
 * Created by melorin on 2017/3/23.
 */
public class PermissionsUtil {

    static final String TAG = "Permissions";

    public static void requestPermissions(OnPermissionResultListener listener, @NonNull String permission, String... permissions) {
        requestPermissions(null, listener, permission, permissions);
    }

    /**
     * 对外开放方法，对系统版本进行区分
     */
    public static void requestPermissions(Activity activity, OnPermissionResultListener listener, @NonNull String permission, String... permissions) {
        // Android M以下系统返回现有权限状态，不做申请操作
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity = checkActivity(activity);
            if (activity == null) {
                L.e("Permissions", "App is background and activity is null");
                if (listener != null) {
                    listener.onRequestPermissionsResult(getGrantedPermissions(permissions), getDeniedPermissions(permissions));
                }
                return;
            }
            List<String> permissionList = new LinkedList<>();
            permissionList.add(permission);
            permissionList.addAll(Arrays.asList(permissions));
            String[] granted = getGrantedPermissions(permissionList.toArray(new String[]{}));
            String[] denied = getDeniedPermissions(permissionList.toArray(new String[]{}));

            if (denied.length == 0) {
                listener.onAllGranted();
            } else {
                listener.onRequestPermissionsResult(granted, denied);
            }
            return;
        }
        List<String> permissionList = new LinkedList<>();
        permissionList.add(permission);
        permissionList.addAll(Arrays.asList(permissions));
        requestPermissions(activity, permissionList.toArray(new String[]{}), 1, listener);
    }

    /**
     * 权限申请入口
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(Activity activity, @NonNull String[] permissions, int requestCode, OnPermissionResultListener listener) {
        // 传入Activity及栈顶Activity均为空时返回现有权限状态
        activity = checkActivity(activity);
        if (activity == null) {
            L.e("Permissions", "App is background and activity is null");
            if (listener != null) {
                listener.onRequestPermissionsResult(getGrantedPermissions(permissions), getDeniedPermissions(permissions));
            }
            return;
        }
        MyPermissionFragment fragment = findPermissionFragment(activity);
        // PermissionFragment初始化完成后根据TAG添加至Activity
        if (fragment == null) {
            fragment = new MyPermissionFragment().context(activity);
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(fragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();

        }
        fragment.requestPermissions(permissions, requestCode, listener);
    }

    /**
     * 获取PermissionFragment
     */
    private static MyPermissionFragment findPermissionFragment(Activity activity) {
        return (MyPermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    /**
     * 校验单个权限
     */
    public static int checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(Utils.getContext(), permission);
    }

    /**
     * 校验单个权限
     */
    public static int checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 对Activity进行校验，返回传入Activity或栈顶Activity
     */
    private static Activity checkActivity(Activity activity) {
        if (activity != null) {
            return activity;
        }
        return ActivityCollector.getTopActivity();
    }

    public static String[] getGrantedPermissions(@NonNull String[] permissions) {
        List<String> granted = new LinkedList<>();
        for (String perm : permissions) {
            if (checkPermission(perm) == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            }
        }
        return granted.toArray(new String[]{});
    }

    public static String[] getDeniedPermissions(@NonNull String[] permissions) {
        List<String> denied = new LinkedList<>();
        for (String perm : permissions) {
            if (checkPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                denied.add(perm);
            }
        }
        return denied.toArray(new String[]{});
    }
}
