package com.example.chenjy.myapplication.dialog.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.adapter.holder.BaseHolder;
import com.example.chenjy.myapplication.adapter.relations.BaseRelations;
import com.example.chenjy.myapplication.dialog.bean.DialogMenu;

import androidx.annotation.NonNull;


/**
 * Create by Melorin on 2019/1/25
 */
public class MenuHolder extends BaseHolder<DialogMenu> {

    public MenuHolder(View itemView) {
        super(itemView);
    }

    public MenuHolder(Context context, BaseRelations relations, ViewGroup parent, int type) {
        super(context, relations, parent, type);
    }

    @Override
    protected void initChildren(View itemView) {
    }

    @Override
    public void onBindHolder(@NonNull DialogMenu model, int position) {
        mHelper.<TextView>findView(R.id.tv_menu, tv -> {
            tv.setText(model.getItem());
            if (model.getColor() != 0) {
                tv.setTextColor(model.getColor());
            }
            tv.setOnClickListener(v -> notifyOperation(model.getId(), position, tv, model));
        });
        if (position == getAdapter().getItemCount() - 1) {
            mHelper.setVisibility(R.id.divider, View.GONE);
        }
    }
}
