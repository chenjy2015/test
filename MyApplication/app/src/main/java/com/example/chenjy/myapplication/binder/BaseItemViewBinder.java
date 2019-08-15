package com.example.chenjy.myapplication.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.chenjy.myapplication.base.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @route:
 * @descript: binder基类 holder与databinding 初始化在这里完成
 * @create: chenjiayou
 * create at 2018/12/28 下午5:48
 */
public abstract class BaseItemViewBinder<object extends Object, VH extends BaseViewHolder, DataBinding extends ViewDataBinding> extends ItemViewBinder<object, VH> {


    protected BaseActivity act;

    protected object o;

    protected object getData() {
        return o;
    }

    public abstract int setView();

    public abstract void initEvent(DataBinding viewDataBinding, BaseViewHolder<DataBinding> holder);

    public abstract void bind(BaseViewHolder<DataBinding> holder, object item);

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(setView(), parent, false);
        DataBinding viewDataBinding = DataBindingUtil.bind(view);
        return (VH) new BaseViewHolder(viewDataBinding);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull object item) {
        if (holder.viewDataBinding.getRoot().getContext() instanceof BaseActivity) {
            act = (BaseActivity) holder.viewDataBinding.getRoot().getContext();
        }
        this.o = item;
        initEvent((DataBinding) holder.viewDataBinding, holder);
        bind(holder, item);
    }
}
