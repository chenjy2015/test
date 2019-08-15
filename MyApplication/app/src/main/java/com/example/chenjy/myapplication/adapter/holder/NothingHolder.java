package com.example.chenjy.myapplication.adapter.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.adapter.relations.BaseRelations;

import androidx.annotation.NonNull;


/**
 * Create by Melorin on 2019/1/2
 */
public class NothingHolder<T> extends BaseHolder<T> {

    public NothingHolder(View itemView) {
        super(itemView);
    }

    public NothingHolder(Context context, BaseRelations relations, ViewGroup parent, int type) {
        super(context, relations, parent, type);
    }

    @Override
    protected void initChildren(View itemView) {

    }

    @Override
    public void onBindHolder(@NonNull T model, int position) {

    }
}
