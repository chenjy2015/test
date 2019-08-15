package com.example.chenjy.myapplication.adapter.holder;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.example.chenjy.myapplication.action.IAction1;


/**
 * Holder装载数据帮助类
 * <p>
 * Created by melorin on 2017/1/28.
 */
public class HolderHelper {

    protected View mItemView;
    protected SparseArray<View> mViewMap = new SparseArray<>();

    public HolderHelper(View itemView) {
        this.mItemView = itemView;
    }

    public <T extends View> T findView(int resId) {
        T view = null;
        try {
            view = (T) mViewMap.get(resId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("make sure using right view type: " + e.getMessage());
        }
        if (view == null && mViewMap.indexOfKey(resId) < 0) {
            view = mItemView.findViewById(resId);
            mViewMap.put(resId, view);
        }
        return view;
    }

    public <T extends View> void findView(int resId, IAction1<T> invoke) {
        T view = findView(resId);
        if (view != null) {
            invoke.invoke(view);
        }
    }

    public <T extends View> T findView(View parent, int resId, IAction1<T> invoke) {
        T view = parent.findViewById(resId);
        if (view != null) {
            invoke.invoke(view);
        }
        return view;
    }

    public void setText(int resId, CharSequence text) {
        if (text == null) {//非null应该也可以被设置
            return;
        }
        TextView view = findView(resId);
        if (checkView(view, resId)) {
            view.setText(text);
        }
    }

    public void setVisibility(int resId, int visibility) {
        View view = findView(resId);
        if (checkView(view, resId)) {
            view.setVisibility(visibility);
        }
    }

    public void setOnClickListener(int resId, View.OnClickListener listener) {
        View view = findView(resId);
        if (checkView(view, resId)) {
            view.setOnClickListener(listener);
        }
    }

    public void setOnLongClickListener(int resId, View.OnLongClickListener listener) {
        View view = findView(resId);
        if (checkView(view, resId)) {
            view.setOnLongClickListener(listener);
        }
    }

    public View getItemView() {
        return mItemView;
    }

    private boolean checkView(View view, int resId) {
        return view != null;
    }
}
