package com.example.chenjy.myapplication.dialog.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.adapter.holder.BaseHolder;
import com.example.chenjy.myapplication.adapter.relations.BaseRelations;
import com.example.chenjy.myapplication.dialog.bean.DialogMenu;

import androidx.annotation.NonNull;


/**
 * Create by Melorin on 2019/1/25
 */
public class BrowserShareHolder extends BaseHolder<DialogMenu> {

    public BrowserShareHolder(View itemView) {
        super(itemView);
    }

    public BrowserShareHolder(Context context, BaseRelations relations, ViewGroup parent, int type) {
        super(context, relations, parent, type);
    }

    @Override
    protected void initChildren(View itemView) {
    }

    @Override
    public void onBindHolder(@NonNull DialogMenu model, int position) {
        mHelper.<ImageView>findView(R.id.tv_icon, iv -> {
            iv.setBackground(getContext().getResources().getDrawable(model.getDrawableRes()));
        });
        mHelper.<TextView>findView(R.id.tv_name, tv -> {
            tv.setText(model.getItem());
        });

        mHelper.getItemView().setOnClickListener(v -> notifyOperation(model.getId(), position,  mHelper.getItemView(), model));
    }
}
