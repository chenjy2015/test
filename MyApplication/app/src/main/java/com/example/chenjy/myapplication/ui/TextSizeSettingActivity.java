package com.example.chenjy.myapplication.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.contants.SettingConst;
import com.example.chenjy.myapplication.databinding.ActivityTextSizeSettingBinding;
import com.example.chenjy.myapplication.utils.TextSizeSettingHelper;
import com.example.chenjy.myapplication.utils.bus.Bus;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.Locale;

public class TextSizeSettingActivity extends BaseUIActivity<ActivityTextSizeSettingBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_text_size_setting;
    }

    @Override
    public void initView() {
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                this, R.anim.m_loading);
        // 使用ImageView显示动画
        mViewDataBinding.loadingImg.startAnimation(hyperspaceJumpAnimation);
    }

    @Override
    public void initEvent() {
        TextSizeSettingHelper helper = new TextSizeSettingHelper(this);
        addDisposable(
                RxView.clicks(mViewDataBinding.sizeSettingBtn)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> helper.show())
        );

        addDisposable(
                Bus.register(this, "SizeSetting", Float.class)
                        .subscribe(this::setTextSizeModel)
        );

    }

    @Override
    public void initData() {

    }

    private static float fontScale = -1; //动态设置放大倍数
    private static float MULTIPLE = 1.0f; //默认放大倍数
    private static String languageType;

    public void setTextSizeModel(float fontScale) {
        this.fontScale = fontScale;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    @Override
    public Resources getResources() {

        Resources resources = super.getResources();
        if (resources != null) {
            if (fontScale < 0) {
                fontScale = 1 * MULTIPLE;
            }
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            // 应用字号设置
            config.fontScale = fontScale;
            // 应用用户选择语言
            if (languageType == null) {
                config.locale = Locale.getDefault();
            } else {
                config.locale = languageType.equals(SettingConst.LanguageType.Chinese) ? Locale.CHINESE : Locale.ENGLISH;
            }
            resources.updateConfiguration(config, dm);
        }
        return resources;
    }

}
