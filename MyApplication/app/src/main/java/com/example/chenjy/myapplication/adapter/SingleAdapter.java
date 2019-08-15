package com.example.chenjy.myapplication.adapter;

import android.content.Context;


import com.example.chenjy.myapplication.adapter.holder.BaseHolder;
import com.example.chenjy.myapplication.adapter.relations.BaseRelations;
import com.example.chenjy.myapplication.adapter.relations.SingleRelations;

import java.util.List;

import androidx.annotation.LayoutRes;

/**
 * Create by Melorin on 2018/4/24
 */
public class SingleAdapter<T> extends BasePureAdapter<T> {

    public SingleAdapter(Context context, List<T> models, @LayoutRes int layoutID, Class<? extends BaseHolder<T>> clazz) {
        super(context, models);
        relations = new SingleRelations<>(layoutID, clazz);
    }

    @Override
    protected BaseRelations<T> initRelations() {
        return relations;
    }


    public void notifyDataSetChanged( List<T> models){
        this.mModels = models;
        super.notifyDataSetChanged();
    }
}
