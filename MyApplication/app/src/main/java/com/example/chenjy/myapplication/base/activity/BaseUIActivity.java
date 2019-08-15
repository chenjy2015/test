package com.example.chenjy.myapplication.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chenjy.myapplication.ViewUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenjy on 2019/4/11.
 */

public abstract class BaseUIActivity<DataBinding extends ViewDataBinding> extends SwipeBackActivity {
    public DataBinding mViewDataBinding;
    public ViewUtils mViewUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        createDataBinding();
        initView();
        init();
        initEvent();
        initData();
    }

    protected void preCreate() { }

    public abstract int getLayout();

    public void init() {
        mViewUtils = new ViewUtils();
    }

    public abstract void initView();

    public abstract void initEvent();

    public abstract void initData();

    public void createDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayout());
    }

    /**
     * 滑动监听 包含到顶部监听和到底部监听
     *
     * @param recyclerView
     * @param onScrollListener
     */
    public void registerScrollChangeListener(RecyclerView recyclerView, ViewUtils.OnScrollListener onScrollListener) {
        mViewUtils.registerScrollChangeListener(recyclerView, onScrollListener);
    }

    /**
     * 根据当前recyclerview 获取当前可见item的索引值
     *
     * @param recyclerView
     * @param onScrollListener 可见索引值变化监听
     */
    public void getVisibleIndexs(RecyclerView recyclerView, ViewUtils.OnScrollListener onScrollListener) {
        mViewUtils.getVisibleIndexs(recyclerView, onScrollListener);
    }

    /**
     * 获取当前recyclerview 一页可加载item的数量
     *
     * @param view
     */
    public int getVisibleCount(View view, int orientation) {
        return mViewUtils.getVisibleCount(view, orientation);
    }

    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
