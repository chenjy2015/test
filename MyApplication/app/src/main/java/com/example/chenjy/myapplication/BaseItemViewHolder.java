package com.example.chenjy.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.base.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by chenjy on 2019/4/11.
 */

public abstract class BaseItemViewHolder<o extends Object, vh extends BaseViewHolder, DataBinding extends ViewDataBinding> extends ItemViewBinder<o, vh> {

    protected BaseActivity act;

    public abstract int setView();

    public abstract void initEvent(DataBinding viewDataBinding, int position);

    public abstract void bind(BaseViewHolder<DataBinding> holder, o item);

    @NonNull
    @Override
    protected vh onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(setView(), parent, false);
        DataBinding viewDataBinding = DataBindingUtil.bind(view);
        return (vh) new BaseViewHolder(viewDataBinding);
    }

    @Override
    protected void onBindViewHolder(@NonNull vh holder, @NonNull o item) {
        if (holder.mViewDataBinding.getRoot().getContext() instanceof BaseActivity) {
            act = (BaseActivity) holder.mViewDataBinding.getRoot().getContext();
        }
        initEvent((DataBinding) holder.mViewDataBinding, holder.getAdapterPosition());
        bind(holder, item);
    }
}
