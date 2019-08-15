package com.example.chenjy.myapplication.binder;

import android.util.DisplayMetrics;

import com.example.chenjy.myapplication.BaseItemViewHolder;
import com.example.chenjy.myapplication.BaseViewHolder;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.databinding.BinderSketchBinding;
import com.example.chenjy.myapplication.sketch.DeviceUtils;
import com.example.chenjy.myapplication.sketch.ImageOptions2;
import com.example.chenjy.myapplication.sketch.ImageType;
import com.jakewharton.rxbinding2.view.RxView;

import me.panpf.sketch.SketchImageView;
import me.panpf.sketch.display.TransitionImageDisplayer;
import me.panpf.sketch.request.DisplayOptions;
import me.panpf.sketch.request.ShapeSize;
import me.panpf.sketch.sample.bean.UnsplashImage;

/**
 * Created by chenjy on 2019/4/25.
 */

public class SketchBinder extends BaseItemViewHolder<UnsplashImage, BaseViewHolder, BinderSketchBinding> {

    ImageType type; // 0 纵向列表 1 详情大图
    OnItemClickListener clickListener;


    public SketchBinder(ImageType type, OnItemClickListener clickListener) {
        this.type = type;
        this.clickListener = clickListener;
    }

    @Override
    public int setView() {
        return R.layout.binder_sketch;
    }

    @Override
    public void initEvent(BinderSketchBinding viewDataBinding, int position) {

    }

    @Override
    public void bind(BaseViewHolder<BinderSketchBinding> holder, UnsplashImage item) {
        switch (type) {
            case IMAGE_TYPE_NORMAL:
                dispalyImage(holder, item);
                break;

            case IMAGE_TYPE_LARGE:
                displayLargeImage(holder, item);
                break;
        }

        act.addDisposable(
                RxView.clicks(holder.mViewDataBinding.getRoot())
                        .subscribe(o -> clickListener.onItemClick(holder.getAdapterPosition()))
        );

    }

    public void dispalyImage(BaseViewHolder<BinderSketchBinding> holder, UnsplashImage item) {
        holder.mViewDataBinding.sketchIcon.setOptions(new ImageOptions2().getDisplayOptions(act, ImageOptions2.LIST_FULL));
        holder.mViewDataBinding.sketchIcon.displayImage(item.getUrls().getSmall());
    }

    public void displayLargeImage(BaseViewHolder<BinderSketchBinding> holder, UnsplashImage item) {
        holder.mViewDataBinding.sketchIcon.setOptions(new ImageOptions2().getDisplayOptions(act, ImageOptions2.ROUND_RECT));
        holder.mViewDataBinding.sketchIcon.setZoomEnabled(true);
        holder.mViewDataBinding.sketchIcon.setShowGifFlagEnabled(R.drawable.ic_gif);
        holder.mViewDataBinding.sketchIcon.displayImage(item.getUrls().getSmall());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
