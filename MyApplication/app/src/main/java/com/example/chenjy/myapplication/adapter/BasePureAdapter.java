package com.example.chenjy.myapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;


import com.example.chenjy.myapplication.adapter.holder.BaseHolder;
import com.example.chenjy.myapplication.adapter.holder.HolderFactory;
import com.example.chenjy.myapplication.utils.L;

import java.util.List;

/**
 * Created by melorin on 2017/6/9.
 */
public abstract class BasePureAdapter<T> extends BaseAdapter<T> {

    public BasePureAdapter(Context context, List<T> models) {
        super(context, models);
    }

    /**
     * 从Relations获取对应IModel的ItemViewType类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position < 0 || position > mModels.size() - 1) {
            return -1;
        }
        return relations.getModelType(mModels.get(checkPosition(position)));
    }

    /**
     * 通过Holder创建工厂生产viewType对应的Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder<T> holder = HolderFactory.createHolder(mContext, relations, parent, viewType);
        if (holder == null) {
            L.e(L.HOLDER, getClass().getCanonicalName() + " Holder is null and view type is " + viewType);
            return null;
        }
        holder.setOnOperationListener(mListener);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        T model = mModels.get(checkPosition(position));
        if (model != null) {
            holder.onBindHolder(model, checkPosition(position));
        } else {
            holder.onBindNull(checkPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public int checkPosition(int position) {
        return position;
    }

}
