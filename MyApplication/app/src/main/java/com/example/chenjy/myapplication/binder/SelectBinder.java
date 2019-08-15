package com.example.chenjy.myapplication.binder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chenjy.myapplication.BaseItemViewHolder;
import com.example.chenjy.myapplication.BaseViewHolder;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.SelectVO;
import com.example.chenjy.myapplication.databinding.BinderSelectBinding;
import com.jakewharton.rxbinding2.view.RxView;

/**
 * Created by chenjy on 2019/4/11.
 */

public class SelectBinder extends BaseItemViewHolder<SelectVO, BaseViewHolder, BinderSelectBinding> {

    OnCheckedChanges changes;

    public SelectBinder(OnCheckedChanges changes) {
        this.changes = changes;
    }

    @Override
    public int setView() {
        return R.layout.binder_select;
    }

    @Override
    public void initEvent(BinderSelectBinding viewDataBinding, int position) {

    }

    @Override
    public void bind(BaseViewHolder<BinderSelectBinding> holder, SelectVO selectVO) {
        holder.mViewDataBinding.setSelectVO(selectVO);
        RxView
                .clicks(holder.mViewDataBinding.getRoot())
                .subscribe(o ->
                        changes.onChange(!selectVO.isSelect, holder.getAdapterPosition()));
        Glide.with(act).load(selectVO.url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(holder.mViewDataBinding.album);
    }

    public interface OnCheckedChanges {
        void onChange(boolean checked, int position);
    }
}
