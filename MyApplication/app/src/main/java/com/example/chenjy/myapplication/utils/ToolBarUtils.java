package com.example.chenjy.myapplication.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.chenjy.myapplication.R;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;

/**
 * @route:
 * @descript: 标题栏操作类
 * @create: chenjiayou
 * create at 2018/11/21 10:30
 */
public class ToolBarUtils {

    Toolbar mToolbar;
    View mBackParent;
    ImageView mToolbarBack; // 左侧退出箭头
    TextView mToolbarLeftTitle; // 左侧标题
    TextView mToolbarBackSubTitle;// 副标题

    TextView mToolbarTitle;// 中间标题
    TextView mToolbarTitleSub;// 中间副标题

    TextView mToolbarMenuText;// 右侧标题
    ImageView mToolbarMenu;// 右侧标题按钮
    ImageView mToolbarMenuSub;// 右侧搜索按钮

    float mLastMenuX;// 记录当前右侧标题按钮触摸点X轴位置
    float mLastMenuY;// 记录当前右侧标题按钮触摸点Y轴位置

    private ToolBarUtils(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
        this.init();
        this.initEvent();
    }

    public static class Build {

        Toolbar toolbar;

        public Build(Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        public ToolBarUtils create() {
            return new ToolBarUtils(toolbar);
        }
    }

    protected void init() {
        mBackParent = mToolbar.findViewById(R.id.v_parent_back);
        mToolbarBack = mToolbar.findViewById(R.id.toolbar_back);
        mToolbarLeftTitle = mToolbar.findViewById(R.id.toolbar_back_title);
        mToolbarBackSubTitle = mToolbar.findViewById(R.id.toolbar_back_sub);

        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarTitleSub = mToolbar.findViewById(R.id.toolbar_title_sub);

        mToolbarMenuText = mToolbar.findViewById(R.id.toolbar_subtitle);
        mToolbarMenu = mToolbar.findViewById(R.id.toolbar_menu);
        mToolbarMenuSub = mToolbar.findViewById(R.id.toolbar_menu_sub);
    }

    protected void initEvent() {
        mToolbarMenu.setOnTouchListener((v, motionEvent) -> {
            mLastMenuX = motionEvent.getRawX();
            mLastMenuY = motionEvent.getRawY();
            return false;
        });
    }

    public ToolBarUtils setLeftTitle(@NonNull CharSequence title) {
        mToolbarLeftTitle.setText(title);
        mToolbarLeftTitle.setVisibility(View.VISIBLE);
        mToolbarBack.setVisibility(View.GONE);
        return this;
    }

    public ToolBarUtils setBackSubText(@NonNull CharSequence title) {
        mToolbarBackSubTitle.setText(title);
        mToolbarBackSubTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setBackImageResource(@DrawableRes int drawable) {
        mToolbarBack.setImageResource(drawable);
        return this;
    }

    public ToolBarUtils setBackSubColor(@ColorInt int colorRes) {
        mToolbarBackSubTitle.setTextColor(colorRes);
        mToolbarBackSubTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setTitle(@NonNull String title) {
        mToolbarTitle.setText(title);
        mToolbarTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setTitle(@NonNull @StringRes int title) {
        mToolbarTitle.setText(title);
        mToolbarTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setSubTitle(@NonNull String title) {
        mToolbarTitleSub.setText(title);
        mToolbarTitleSub.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setSubTitle(@NonNull @StringRes int title) {
        mToolbarTitleSub.setText(title);
        mToolbarTitleSub.setVisibility(View.VISIBLE);
        return this;
    }

    public ToolBarUtils setMenuText(String menuText) {
        if (TextUtils.isEmpty(menuText)) {
            mToolbarMenuText.setVisibility(View.GONE);
        } else {
            mToolbarMenuText.setText(menuText);
            mToolbarMenuText.setVisibility(View.VISIBLE);
        }
        if (mToolbarMenu.getVisibility() == View.VISIBLE) {
            mToolbarMenu.setVisibility(View.GONE);
        }
        return this;
    }

    public ToolBarUtils setMenuText(@StringRes int menuText) {
        mToolbarMenuText.setText(menuText);
        mToolbarMenuText.setVisibility(View.VISIBLE);
        if (mToolbarMenu.getVisibility() == View.VISIBLE) {
            mToolbarMenu.setVisibility(View.GONE);
        }
        return this;
    }

    public String getMenuText() {
        return mToolbarMenuText.getText().toString().trim();
    }

    public ToolBarUtils setMenuImgResource(int resource) {
        mToolbarMenu.setImageResource(resource);
        mToolbarMenu.setVisibility(View.VISIBLE);
        mToolbarMenuText.setVisibility(View.GONE);
        return this;
    }

    public ToolBarUtils showSubMenu(boolean isShow) {
        mToolbarMenuSub.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public ToolBarUtils setSubMenuImageResource(@DrawableRes int res) {
        mToolbarMenuSub.setVisibility(View.VISIBLE);
        mToolbarMenuSub.setImageResource(res);
        return this;
    }

    public ToolBarUtils setBackClick(View.OnClickListener onClickListener) {
        mBackParent.setOnClickListener(onClickListener);
        return this;
    }

    public ToolBarUtils setBackTitleClick(View.OnClickListener onClickListener) {
        mToolbarLeftTitle.setOnClickListener(onClickListener);
        return this;
    }

    public ToolBarUtils setMenuTextCallBack(View.OnClickListener onClickListener) {
        mToolbarMenuText.setOnClickListener(onClickListener);
        return this;
    }

    public ToolBarUtils setMenuCallBack(View.OnClickListener onClickListener) {
        mToolbarMenu.setOnClickListener(onClickListener);
        return this;
    }

    public ToolBarUtils setSubMenuCallBack(View.OnClickListener onClickListener) {
        mToolbarMenuSub.setOnClickListener(onClickListener);
        return this;
    }

    public TextView getMenuTextTv() {
        return mToolbarMenuText;
    }

    public void hideToolBar() {
        mToolbar.setVisibility(View.GONE);
    }

    public void showToolBar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    public ImageView getToolbarBack() {
        return mToolbarBack;
    }

    public ImageView getToolBarMenu() {
        return mToolbarMenu;
    }

    public float getLastMenuX() {
        return mLastMenuX;
    }

    public float getLastMenuY() {
        return mLastMenuY;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
