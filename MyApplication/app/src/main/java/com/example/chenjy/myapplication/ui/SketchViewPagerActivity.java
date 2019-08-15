package com.example.chenjy.myapplication.ui;

import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivitySketchViewPagerBinding;
import com.example.chenjy.myapplication.sketch.ImageOptions;
import com.example.chenjy.myapplication.sketch.ImageOptions2;
import com.example.chenjy.myapplication.utils.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import androidx.viewpager.widget.PagerAdapter;
import me.panpf.sketch.Sketch;
import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.sample.bean.UnsplashImage;

public class SketchViewPagerActivity extends BaseUIActivity<ActivitySketchViewPagerBinding> {


    List<UnsplashImage> mImgList = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.activity_sketch_view_pager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void init() {
        super.init();
        String content = ResourceUtils.readAssets2String("resource.txt");
        Gson gson = new Gson();
        mImgList = gson.fromJson(content, new TypeToken<ArrayList<UnsplashImage>>() {
        }.getType());
        if (mImgList == null) {
            mImgList = new ArrayList<>();
        }
    }

    @Override
    public void initData() {
        setAdapter();
    }

    private void setAdapter() {
        final LinkedList<SketchImageView> viewCache = new LinkedList<>();

        mViewDataBinding.viewPager.setAdapter(new PagerAdapter() {

            private void display(SketchImageView sketchImageView, String url) {
                Sketch.with(SketchViewPagerActivity.this).getConfiguration().getDiskCache();
                Sketch.with(SketchViewPagerActivity.this).getConfiguration().getMemoryCache().clear();
                sketchImageView.setOptions(new ImageOptions2().getDisplayOptions(SketchViewPagerActivity.this, ImageOptions.LIST_FULL));
                sketchImageView.setZoomEnabled(true);
                sketchImageView.getOptions().setCacheInMemoryDisabled(false);
                sketchImageView.getOptions().setCacheInDiskDisabled(false);
                sketchImageView.setShowImageFromEnabled(true);
                sketchImageView.setShowDownloadProgressEnabled(true, Color.parseColor("#ff0000"));
                sketchImageView.setShowPressedStatusEnabled(true);
                sketchImageView.getOptions().setThumbnailMode(true);
                Sketch.with(SketchViewPagerActivity.this)
                        .display(StringUtils.null2Length0(url), sketchImageView)
                        .commit();
            }

            @Override
            public int getCount() {
                return mImgList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                SketchImageView sketchImageView;
                if (viewCache.size() > 0 && position < viewCache.size()) {
                    sketchImageView = viewCache.remove(position);
                } else {
                    sketchImageView = new SketchImageView(SketchViewPagerActivity.this);
                    sketchImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
                if (position == 2) {
                    String path = Environment.getExternalStorageDirectory() + File.separator + "IMG_20181012_151500.jpg";
                    L.d("display", "IMG_20181012_151500.jpg is exists : " + FileUtils.isFileExists(new File(path)));
                    display(sketchImageView, path);
                } else if (position == 3) {
                    String path = Environment.getExternalStorageDirectory() + File.separator + "mypicture.jpg";
                    L.d("display", "IMG_20181012_151500.jpg is exists : " + FileUtils.isFileExists(new File(path)));
                    display(sketchImageView, path);
                } else {
                    display(sketchImageView, Objects.requireNonNull(mImgList.get(position).getUrls()).getSmall());
                }
                addDisposable(
                        RxView.clicks(sketchImageView)
                                .subscribe(o -> {
                                    ToastUtils.showShort("on click !");
                                })
                );
                container.addView(sketchImageView);
                return sketchImageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                SketchImageView sketchImageView = (SketchImageView) object;
                container.removeView(sketchImageView);
                viewCache.add(sketchImageView);
            }
        });
    }
}
