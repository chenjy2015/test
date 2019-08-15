package com.example.chenjy.myapplication;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenjy on 2019/4/11.
 */

public class BaseViewHolder<DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public DataBinding mViewDataBinding;

    public BaseViewHolder(@NonNull DataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.mViewDataBinding = viewDataBinding;
    }
}
