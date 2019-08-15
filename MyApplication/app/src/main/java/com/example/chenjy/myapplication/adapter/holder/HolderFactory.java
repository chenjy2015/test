package com.example.chenjy.myapplication.adapter.holder;

import android.content.Context;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.adapter.relations.BaseRelations;

public class HolderFactory {

    /**
     * 通过关系映射表寻找对应关系，通过反射创建对应Holder
     *
     * @param context
     * @param parent
     * @param itemViewType
     * @return
     */
    public static <T> BaseHolder<T> createHolder(Context context, BaseRelations<T> relations, ViewGroup parent, int itemViewType) {
        BaseHolder holder = null;
        Class<? extends BaseHolder> clazz = relations.getHolder(itemViewType);
        try {
            holder = clazz.getDeclaredConstructor(Context.class, BaseRelations.class, ViewGroup.class, int.class).newInstance(context, relations, parent, itemViewType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder;
    }
}
