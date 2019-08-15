
package com.example.chenjy.myapplication.base.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.base.i.IViewModel;
import com.example.chenjy.myapplication.widget.SwipeBackLayout;

import androidx.annotation.DrawableRes;


/**
 * @route:
 * @descript: Activity 基类 绑定SwipeBackLayout实现可滑动关闭当前Activity
 * @create: chenjiayou
 * create at 2018/11/21 9:38
 */
public class SwipeBackActivity<ViewModel extends IViewModel> extends BaseActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private int mDefaultFragmentBackground = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return view;
    }

    private void onActivityCreate() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
    }

    protected void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
        mSwipeBackLayout.setEdgeLevel(edgeLevel);
    }

    protected void setEdgeLevel(int widthPixel) {
        mSwipeBackLayout.setEdgeLevel(widthPixel);
    }

    protected SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    protected void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    public boolean swipeBackPriority() {
        return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    protected void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        mDefaultFragmentBackground = backgroundRes;
    }

    public int getDefaultFragmentBackground() {
        return mDefaultFragmentBackground;
    }
}
