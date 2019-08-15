package com.example.chenjy.myapplication.adapter.relations;

import com.example.chenjy.myapplication.adapter.holder.BaseHolder;

import androidx.annotation.LayoutRes;


/**
 * Created by melorin on 2018/4/9.
 */
public class SingleRelations<T> extends BaseRelations<T> {

    private Class<? extends BaseHolder> mClazz;
    private int mLayoutId;

    public SingleRelations(@LayoutRes int layoutId, Class<? extends BaseHolder> clazz) {
        this.mClazz = clazz;
        mLayoutId = layoutId;
    }

    @Override
    public int getResource(int type) {
        return mLayoutId;
    }

    @Override
    public Class<? extends BaseHolder> getHolder(int type) {
        return mClazz;
    }

    @Override
    protected void initRelations() {
    }

    @Override
    public int getModelType(T model) {
        return 1;
    }
}