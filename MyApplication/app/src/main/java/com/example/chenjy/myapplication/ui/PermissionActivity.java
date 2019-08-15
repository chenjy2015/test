package com.example.chenjy.myapplication.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityPermissionBinding;
import com.example.chenjy.myapplication.permission.OnPermissionResultListener;
import com.example.chenjy.myapplication.permission.PermissionsUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionActivity extends BaseUIActivity<ActivityPermissionBinding> {


    RxPermissions rxPermissions;

    @Override
    public int getLayout() {
        return R.layout.activity_permission;
    }

    @Override
    public void initView() {
        LogUtils.d("...................................init.....................................");
    }

    @Override
    public void initEvent() {
        addDisposable(
                RxView.clicks(mViewDataBinding.record)
                        .subscribe(o -> requestPermission2())
        );
    }

    @Override
    public void initData() {
        rxPermissions = new RxPermissions(this);
    }

    public void requestPermission() {
        if (PermissionsUtil.checkPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || PermissionsUtil.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionsUtil.requestPermissions(this, new OnPermissionResultListener() {
                @Override
                public void onRequestPermissionsResult(String[] granted, String[] denied) {
                    ToastUtils.showShort("您需要打开录音权限");
                }

                @Override
                public void onAllGranted() {
                    ToastUtils.showShort("录音权限已打开");
                }
            }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
    }

    public void requestPermission2() {
        addDisposable(
                rxPermissions.request(Manifest.permission.RECORD_AUDIO)
                        .subscribe(granted -> {
                            if (granted) {
                                // All requested permissions are granted
                                ToastUtils.showShort("录音权限已打开");
                            } else {
                                // At least one permission is denied
                                ToastUtils.showShort("您需要打开录音权限");
                            }
                        })
        );
        LogUtils.d("....................................................................................");
    }
}
