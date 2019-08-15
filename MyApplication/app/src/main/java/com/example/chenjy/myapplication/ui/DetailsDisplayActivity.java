package com.example.chenjy.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityScrollDisplayBinding;
import com.example.chenjy.myapplication.databinding.BinderSketchBinding;
import com.example.chenjy.myapplication.sketch.DeviceUtils;
import com.example.chenjy.myapplication.sketch.ImageOptions2;
import com.example.chenjy.myapplication.sketch.ZoomOutPageTransformer;
import com.example.chenjy.myapplication.ui.fragment.DetailsDisplayFragment;
import com.example.chenjy.myapplication.utils.bus.Bus;
import com.example.chenjy.myapplication.utils.event.ViewPagerScrollEvent;
import com.example.chenjy.myapplication.utils.toolbar_status.StatusBarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.display.TransitionImageDisplayer;
import me.panpf.sketch.request.DisplayOptions;
import me.panpf.sketch.request.ShapeSize;
import me.panpf.sketch.sample.bean.UnsplashImage;

public class DetailsDisplayActivity extends BaseUIActivity<ActivityScrollDisplayBinding> {

    public static final String KEY_IMAGES = "images";
    public static final String KEY_INDEX = "index";


    List<UnsplashImage> mImgList = new ArrayList<>();

    public static Intent launch(Context context, int index, String imagesJson) {
        Intent intent = new Intent(context, DetailsDisplayActivity.class);
        intent.putExtra(KEY_IMAGES, imagesJson);
        intent.putExtra(KEY_INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setRootViewFitsSystemWindows(this, false);
        StatusBarUtils.setTranslucentStatus(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_scroll_display;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {

        addDisposable(
                Bus.register(this, "change", ViewPagerScrollEvent.class)
                        .subscribe(viewPagerScrollEvent ->
                                displayLargeImage(mViewDataBinding.sketchIv, mImgList.get(viewPagerScrollEvent.position)))
        );

    }

    @Override
    public void initData() {
        int index = getIntent().getIntExtra(KEY_INDEX, 0);
        String imagesJson = getIntent().getStringExtra(KEY_IMAGES);
        if (StringUtils.isEmpty(imagesJson)) {
            showAlert();
        }
        try {
            mImgList = new Gson().fromJson(imagesJson, new TypeToken<List<UnsplashImage>>() {
            }.getType());
        } catch (JsonParseException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DetailsDisplayFragment detailsDisplayFragment = DetailsDisplayFragment.newInstance(index, imagesJson);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, detailsDisplayFragment).commit();

        displayLargeImage(mViewDataBinding.sketchIv, mImgList.get(index));
    }

    public void showAlert() {
        new AlertDialog.Builder(this)
                .setMessage("列表不能为空!")
                .setCancelable(false)
                .setPositiveButton("", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .create();

    }

    public void displayLargeImage(SketchImageView sketchImageView, UnsplashImage item) {
        sketchImageView.setOptions(new ImageOptions2().getDisplayOptions(this, ImageOptions2.WINDOW_BACKGROUND));
        sketchImageView.setZoomEnabled(true);
        setLayoutParams(sketchImageView, item);
        sketchImageView.displayImage(item.getUrls().getSmall());
    }

    public void setLayoutParams(SketchImageView sketchImageView, UnsplashImage item) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (sketchImageView.getLayoutParams() != null) {
            sketchImageView.getLayoutParams().width = displayMetrics.widthPixels;
            sketchImageView.getLayoutParams().height = displayMetrics.heightPixels;
        }
    }

}
