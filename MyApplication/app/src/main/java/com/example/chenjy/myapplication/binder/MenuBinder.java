package com.example.chenjy.myapplication.binder;

import com.example.chenjy.myapplication.BaseItemViewHolder;
import com.example.chenjy.myapplication.BaseViewHolder;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.databinding.BinderMenuBinding;
import com.jakewharton.rxbinding2.view.RxView;

public class MenuBinder extends BaseItemViewHolder<String, BaseViewHolder, BinderMenuBinding> {

    OnMenuClickListener onMenuClickListener;

    public MenuBinder(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    @Override
    public int setView() {
        return R.layout.binder_menu;
    }

    @Override
    public void initEvent(BinderMenuBinding viewDataBinding, int position) {

    }

    @Override
    public void bind(BaseViewHolder<BinderMenuBinding> holder, String item) {
        holder.mViewDataBinding.menuBtn.setText(item);
        act.addDisposable(
                RxView.clicks(holder.mViewDataBinding.menuBtn)
                        .subscribe(o -> onMenuClickListener.onMenuClick(item, holder.getAdapterPosition()))
        );
    }

    public interface OnMenuClickListener {
        void onMenuClick(String name, int index);
    }
}
