package com.example.chenjy.myapplication.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenjy.myapplication.adapter.BaseAdapter;
import com.example.chenjy.myapplication.adapter.OnOperationListener;
import com.example.chenjy.myapplication.adapter.relations.BaseRelations;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Holder基类
 * 包含：
 * 1、绑定View及对应子View初始化工作
 * 2、绑定对应Model的相应显示操作
 * 3、传递点击事件到上层
 * <p>
 * Created by melorin on 2017/1/19.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public static final int RESOURCE = -1;
    protected HolderHelper mHelper;
    protected Context mContext;
    protected int type;
    private OnOperationListener<T> mListener;
    private BaseAdapter<T> adapter;

    public BaseHolder(View itemView) {
        super(itemView);
        mHelper = new HolderHelper(this.itemView);
    }

    /**
     * 自定义构造方式，保证获得父View的LayoutParams，不添加进父View
     */
    public BaseHolder(Context context, BaseRelations relations, ViewGroup parent, int type) {
        super(LayoutInflater.from(context).inflate(relations.getResource(type), parent, false));
        this.mContext = context;
        this.type = type;
        mHelper = new HolderHelper(this.itemView);
        initChildren(this.itemView);
    }

    public void setOnOperationListener(OnOperationListener<T> listener) {
        this.mListener = listener;
    }

    /**
     * 子View初始化操作
     */
    protected void initChildren(View itemView) {}

    /**
     * 绑定Model后的数据填充方法
     */
    public abstract void onBindHolder(@NonNull T model, int position);

    public BaseAdapter<T> getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter<T> adapter) {
        this.adapter = adapter;
    }

    protected void notifyOperation(int type, int position, View view, T model) {
        if (mListener != null) {
            mListener.onOperation(type, position, view, model);
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public void onViewAttachedToWindow() {
    }

    public void onViewDetachedFromWindow() {
    }

    public void onBindNull(int position){
    }
}
