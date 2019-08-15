package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.adapter.holder.HolderHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PopupAdDialogHelper extends AbstractDialogHelper {
    private List<PopupBean> popupBeanList;

    public PopupAdDialogHelper() {
        super();
    }

    @Override
    protected Dialog createDialog(Activity activity) {
        return new AppCompatDialog(activity, R.style.Dialog);
    }

    public PopupAdDialogHelper setMedia(List<PopupBean> popupBeanList) {
        this.popupBeanList = popupBeanList;
        return this;
    }

    @Override
    protected void setDialogAttrs(Activity activity, Dialog dialog) {
        super.setDialogAttrs(activity, dialog);
        setWindowWith(activity, dialog);
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_popup_ad, null);
        final HolderHelper helper = new HolderHelper(view);

        final ImageView ivClose = helper.findView(R.id.iv_close);
        ViewPager viewPager = helper.findView(R.id.vp_dialog_image);
        LinearLayout indicatorGroup = helper.findView(R.id.viewGroup);
        List<View> advPics = new ArrayList<>();

        for (int i = 0; i < popupBeanList.size(); i++) {
            ImageView imageView = new ImageView(context);
            advPics.add(imageView);
        }
        ImageView[] imageViews = new ImageView[advPics.size()];

        if (advPics.size() > 1) {
            for (int i = 0; i < advPics.size(); i++) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
                layoutParams.leftMargin = 10;
                imageView.setLayoutParams(layoutParams);
                imageViews[i] = imageView;
                if (i == 0) {
                    imageViews[i].setBackgroundResource(R.drawable.icon_page_indicator_normal);
                } else {
                    imageViews[i].setBackgroundResource(R.drawable.icon_page_indicator_focused);
                }
                indicatorGroup.addView(imageViews[i]);
            }
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return popupBeanList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PopupBean bean = popupBeanList.get(position);
                String downloadUrl = bean.getDownloadUrl();
                ImageView imageView = (ImageView) advPics.get(position);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context)
                        .load(downloadUrl).into(imageView);
                imageView.setOnClickListener(v -> {
                    if (mDialogClickListener != null) {
                        mDialogClickListener.onClick(dialog,bean.getClickUrl(),-1);
                    }
                });
                container.addView(imageView, 0);
                return imageView;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.length; i++) {
                    imageViews[position]
                            .setBackgroundResource(R.drawable.icon_page_indicator_normal);
                    if (position != i) {
                        imageViews[i]
                                .setBackgroundResource(R.drawable.icon_page_indicator_focused);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ivClose.setOnClickListener(v -> dialog.dismiss());

        return view;
    }

    private void setWindowWith(Activity context, Dialog dialog) {
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = (int) (d.getWidth() * 0.75);
        window.setAttributes(p);
    }

    public static class PopupBean {
        private String downloadUrl;
        private String clickUrl;

        public PopupBean() {
        }

        public PopupBean(String downloadUrl, String clickUrl) {
            this.downloadUrl = downloadUrl;
            this.clickUrl = clickUrl;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public String getClickUrl() {
            return clickUrl;
        }
    }
}
