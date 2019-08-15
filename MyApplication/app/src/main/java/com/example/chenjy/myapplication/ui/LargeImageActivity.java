package com.example.chenjy.myapplication.ui;

import android.content.Context;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chenjy.myapplication.BuildUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityLargeImageBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @route:
 * @descript: 大图片浏览界面
 * @create: chenjiayou
 * create at 2018/12/18 下午2:32
 */
public class LargeImageActivity extends BaseUIActivity<ActivityLargeImageBinding> {

    List<String> mImgList = new ArrayList();

    @Override
    public void recreate() {
        super.recreate();
        if (BuildUtils.matchVersion()) {
            getWindow().setEnterTransition(new Fade().setDuration(300).setInterpolator(new AccelerateInterpolator()));
            getWindow().setReturnTransition(new Fade().setDuration(300).setInterpolator(new AccelerateInterpolator()));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_large_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            mImgList.add("http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg");
        }
        mViewDataBinding.viewPager.setAdapter(new MyAdapter());
        mViewDataBinding.viewPager.setCurrentItem(0);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            if (container.findViewWithTag(position) != null) {
//                return container.findViewWithTag(position);
//            }
            View view = LayoutInflater.from(LargeImageActivity.this).inflate(R.layout.item_pager_adapter, null);
            view.setTag(position);
            TextView title = view.findViewById(R.id.title);
            title.setText(String.valueOf(position));
            //图片信息设置
//            final PhotoView iv = view.findViewById(R.id.img);
//            iv.enable();
//            iv.enableRotate();
            final ImageView iv = view.findViewById(R.id.img);
            String url = mImgList.get(position);
            ViewCompat.setTransitionName(iv, url + position);

            //加载图片 为避免photoview显示图片偏移 这里选择加载图片资源后显示控件
            loadImg(LargeImageActivity.this, iv, url);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public void loadImg(Context context, ImageView iv, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .thumbnail(0.8f)
                .into(iv);
    }
}
