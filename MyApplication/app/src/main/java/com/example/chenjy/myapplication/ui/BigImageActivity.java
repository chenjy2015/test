package com.example.chenjy.myapplication.ui;

import android.os.Environment;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityBigImageBinding;

import java.io.File;

public class BigImageActivity extends BaseUIActivity<ActivityBigImageBinding> {

    String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1532588239&di=78b4c6bde1cf9d1df89562241b547e72&src=http://p2.qhimg.com/t011fc13354f12d1a46.jpg";

    @Override
    public int getLayout() {
        return R.layout.activity_big_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        String root = Environment.getExternalStorageDirectory().getPath();
        String path = root + File.separator + "Screenshot_2018-11-06-15-05-47-043_com.tencent.reading.png";
        Glide.with(this).load(path).into(mViewDataBinding.ivBig);
    }
}
