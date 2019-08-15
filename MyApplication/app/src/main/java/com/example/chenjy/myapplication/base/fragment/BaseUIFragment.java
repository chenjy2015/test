package com.example.chenjy.myapplication.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseActivity;
import com.example.chenjy.myapplication.base.i.IViewModel;
import com.example.chenjy.myapplication.utils.ToolBarUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * @route:
 * @descript: Fragment 基类 指定了调用方法顺序逻辑 不可随意切换
 * @create: chenjiayou
 * create at 2018/11/20 20:23
 */
public abstract class BaseUIFragment<DataBinding extends ViewDataBinding> extends SwipeBackFragment<DataBinding> {

    public static final String TAG = BaseActivity.class.getSimpleName();
    public Activity mActivity;
    public DataBinding mViewDataBinding;
    public ToolBarUtils mToolBarUtils;

    protected OnAddFragmentListener mAddFragmentListener;
    protected BackHandlerInterface mBackHandlerInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        if (context instanceof OnAddFragmentListener) {
            mAddFragmentListener = (OnAddFragmentListener) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mAddFragmentListener = null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        Toolbar toolbar = mViewDataBinding.getRoot().findViewById(R.id.toolbar);
        if (toolbar != null) {
            mToolBarUtils = new ToolBarUtils.Build(toolbar).create();
//            mToolBarUtils.setBackClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mActivity.onBackPressed();
//                }
//            });
        }
        return attachToSwipeBack(mViewDataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onActivityCreated(savedInstanceState);
        //以下方法不可随意切换顺序调用
        initView();
        init();
        initEvent();
        initData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void preCreate() {

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void init();

    protected abstract void initEvent();

    protected abstract void initData();

    protected ToolBarUtils getToolbar() {
        return mToolBarUtils;
    }

    public interface OnAddFragmentListener {
        void onAddFragment(Fragment fromFragment, Fragment toFragment);
    }

    public interface BackHandlerInterface {
        void setSelectedFragment(BaseUIFragment backHandledFragment);
    }

}
