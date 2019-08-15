package com.example.chenjy.myapplication.binder;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenjy.myapplication.BaseItemViewHolder;
import com.example.chenjy.myapplication.BaseViewHolder;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.databinding.BinderPinyinSortBinding;
import com.example.chenjy.myapplication.parser.SortModel;
import com.jakewharton.rxbinding2.view.RxView;

public class SortBinder extends BaseItemViewHolder<SortModel, BaseViewHolder, BinderPinyinSortBinding> {

    @Override
    public int setView() {
        return R.layout.binder_pinyin_sort;
    }

    @Override
    public void initEvent(BinderPinyinSortBinding viewDataBinding, int position) {

    }

    @Override
    public void bind(BaseViewHolder<BinderPinyinSortBinding> holder, SortModel item) {
        holder.mViewDataBinding.setSort(item);
        act.addDisposable(
                RxView.clicks(holder.mViewDataBinding.parent)
                        .subscribe(o -> {
                            ToastUtils.showShort("letters : " + item.getSortLetters());
                            String s  = null;
                            Integer.parseInt(s);
                        })
        );
    }
}
