package com.example.chenjy.myapplication.ui;

import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityZipBinding;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

public class ZipActivity extends BaseUIActivity<ActivityZipBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_zip;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        addDisposable(
                RxView.clicks(mViewDataBinding.zip).subscribe(o -> {
                    String srcPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "wx2019" + File.separator + "log";
                    String zipPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "wx2019" + File.separator + "logZip" + File.separator + "logSS.zip";
                    FileUtils.createOrExistsFile(zipPath);
                    ZipUtils.zipFile(srcPath, zipPath);
                    ToastUtils.showShort("压缩完成!");
                })
        );
    }

    @Override
    public void initData() {

    }
}
