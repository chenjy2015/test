package com.example.chenjy.myapplication.permission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.chenjy.myapplication.utils.L;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * 权限申请实现Fragment
 * <p>
 * Created by melorin on 2017/3/23.
 */
public class MyPermissionFragment extends Fragment {

    private int mRequestCode = 0;
    private OnPermissionResultListener mListener;
    private Context mContext;
    private List<String> mGranted = new LinkedList<>();

    public MyPermissionFragment() {
    }

    public MyPermissionFragment context(Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * 权限申请方法
     *
     * @param permissions
     * @param requestCode
     * @param listener
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions, int requestCode, OnPermissionResultListener listener) {
//        this.mRequestCode = requestCode;
        this.mListener = listener;
        mGranted.clear();
        List<String> permissionList = new LinkedList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                mGranted.add(permission);
            }
        }
        if (mGranted.size() == permissions.length) {
            L.test("All Granted already");
            if (mListener != null) {
                mListener.onAllGranted();
            }
            return;
        }
        permissions = permissionList.toArray(permissions);
        L.test("requestPermissions");
        this.requestPermissions(permissions, requestCode);
    }

    /**
     * 权限申请结果分发
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mRequestCode == requestCode) {
            List<String> denied = new LinkedList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mGranted.add(permissions[i]);
                } else {
                    denied.add(permissions[i]);
                }
            }
            if (mListener != null) {
                if (denied.isEmpty()) {
                    mListener.onAllGranted();
                } else {
                    mListener.onRequestPermissionsResult(mGranted.toArray(new String[]{}), denied.toArray(new String[]{}));
                }
            }
        }
        mRequestCode++;
    }
}
