package com.example.chenjy.myapplication.binder;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public DataBinding viewDataBinding;

    public BaseViewHolder(@NonNull DataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
    }
}
