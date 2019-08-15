package com.example.chenjy.myapplication.binder;

import android.view.View;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.SessionTextSizeVO;
import com.example.chenjy.myapplication.databinding.BinderSessionTextsizeBinding;
import com.jakewharton.rxbinding2.view.RxView;

public class SessionTextSizeBinder extends BaseItemViewBinder<SessionTextSizeVO, BaseViewHolder, BinderSessionTextsizeBinding> {
    OnItemClickListener onItemClickListener;

    public SessionTextSizeBinder(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int setView() {
        return R.layout.binder_session_textsize;
    }

    @Override
    public void initEvent(BinderSessionTextsizeBinding viewDataBinding, com.example.chenjy.myapplication.binder.BaseViewHolder<BinderSessionTextsizeBinding> holder) {
        act.addDisposable(
                RxView.clicks(viewDataBinding.getRoot())
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> onItemClickListener.onItemClick(holder.getAdapterPosition()))
        );
    }

    @Override
    public void bind(com.example.chenjy.myapplication.binder.BaseViewHolder<BinderSessionTextsizeBinding> holder, SessionTextSizeVO item) {
        holder.viewDataBinding.tvName.setText(item.getName());
        holder.viewDataBinding.tvName.setTextSize(14 * Float.parseFloat(item.getSize()));
        holder.viewDataBinding.ivChecked.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
