package com.example.chenjy.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.chenjy.myapplication.BitmapUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityScaleImageBinding;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ScaleImageActivity extends BaseUIActivity<ActivityScaleImageBinding> {


    public String url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";

    @Override
    public int getLayout() {
        return R.layout.activity_scale_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        loadImg(this, mViewDataBinding.scaleImg, url);
    }

    public void loadImg(Context context, ImageView iv, String url) {
//        Glide.with(context)
//                .load(url)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
//                .thumbnail(0.8f)
//                .into(iv);

        addDisposable(
                Observable.create(e -> {
                    Bitmap bm = BitmapUtils.loadBitmap(this, url);
                    e.onNext(bm);
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            Bitmap bm = (Bitmap) o;
                            mViewDataBinding.scaleImg.setImageBitmap(bm);
                        })
        );

    }
}
