package com.example.chenjy.myapplication.ui.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.fragment.BaseUIFragment;
import com.example.chenjy.myapplication.databinding.BinderSketchBinding;
import com.example.chenjy.myapplication.databinding.FragmentDetailsDisplayBinding;
import com.example.chenjy.myapplication.sketch.DeviceUtils;
import com.example.chenjy.myapplication.sketch.ImageOptions2;
import com.example.chenjy.myapplication.sketch.ZoomOutPageTransformer;
import com.example.chenjy.myapplication.ui.DetailsDisplayActivity;
import com.example.chenjy.myapplication.utils.bus.Bus;
import com.example.chenjy.myapplication.utils.event.ViewPagerScrollEvent;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.display.TransitionImageDisplayer;
import me.panpf.sketch.request.DisplayOptions;
import me.panpf.sketch.request.ShapeSize;
import me.panpf.sketch.sample.bean.UnsplashImage;

public class DetailsDisplayFragment extends BaseUIFragment<FragmentDetailsDisplayBinding> {


    public static final String KEY_IMAGES = "images";
    public static final String KEY_INDEX = "index";


    List<UnsplashImage> mImgList = new ArrayList<>();

    public static DetailsDisplayFragment newInstance(int index, String imagesJson) {
        Bundle args = new Bundle();
        args.putInt(KEY_INDEX, index);
        args.putString(KEY_IMAGES, imagesJson);
        DetailsDisplayFragment fragment = new DetailsDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_details_display;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initEvent() {
        mViewDataBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Bus.post("change", new ViewPagerScrollEvent(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        int index = getArguments().getInt(KEY_INDEX, 0);
        String imagesJson = getArguments().getString(KEY_IMAGES);
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
        mViewDataBinding.viewPager.setAdapter(new MyViewPagerAdapter());
        mViewDataBinding.viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mViewDataBinding.viewPager.setCurrentItem(index);
    }


    public class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BinderSketchBinding dataBinding = DataBindingUtil.bind(LayoutInflater.from(mActivity).inflate(R.layout.binder_sketch, null));
            displayLargeImage(dataBinding.sketchIcon, mImgList.get(position));
            container.addView(dataBinding.getRoot());
            return dataBinding.getRoot();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public void displayLargeImage(SketchImageView sketchImageView, UnsplashImage item) {
//            setLayoutParams(sketchImageView, item);
            sketchImageView.setOptions(new ImageOptions2().getDisplayOptions(mActivity, ImageOptions2.LIST_FULL));
            sketchImageView.setZoomEnabled(true);
            sketchImageView.displayImage(StringUtils.null2Length0(item.getUrls().getSmall()));
        }

    }
}
