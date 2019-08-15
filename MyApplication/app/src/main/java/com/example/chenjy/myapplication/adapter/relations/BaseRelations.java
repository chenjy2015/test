package com.example.chenjy.myapplication.adapter.relations;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.example.chenjy.myapplication.adapter.holder.BaseHolder;

import androidx.annotation.LayoutRes;


/**
 * Created by melorin on 2017/1/28.
 */
public abstract class BaseRelations<T> {

    public static final int HEADER_VIEW = 1;
    public static final int FOOTER_VIEW = 2;

    /**
     * ItemViewType类型与layout映射关系
     */
    protected SparseIntArray typeResourceRelations;
    /**
     * ItemViewType与Holder映射关系
     */
    protected SparseArray<Class<? extends BaseHolder>> typeHolderRelations;

    /**
     * ModelType与ItemViewType映射关系
     */

    public BaseRelations() {
        typeResourceRelations = new SparseIntArray();
        typeHolderRelations = new SparseArray<>();
        initRelations();
    }

    /**
     * 初始化映射关系
     */
    protected abstract void initRelations();

    protected void registerRelations(int type, @LayoutRes int res, Class<? extends BaseHolder> holderClass) {
        typeResourceRelations.put(type, res);
        typeHolderRelations.put(type, holderClass);
    }

    public int getResource(int type) {
        return typeResourceRelations.get(type);
    }

    public Class<? extends BaseHolder> getHolder(int type) {
        return typeHolderRelations.get(type);
    }

    public abstract int getModelType(T model);
}
