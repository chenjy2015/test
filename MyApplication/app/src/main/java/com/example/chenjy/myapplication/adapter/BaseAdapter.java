package com.example.chenjy.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.example.chenjy.myapplication.adapter.holder.BaseHolder;
import com.example.chenjy.myapplication.adapter.holder.HolderFactory;
import com.example.chenjy.myapplication.adapter.relations.BaseRelations;
import com.example.chenjy.myapplication.utils.L;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView基础适配器
 * <p>
 * Created by melorin on 2017/1/19.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected List<T> mModels;
    protected Context mContext;
    protected BaseRelations<T> relations;
    protected OnOperationListener<T> mListener;

    /**
     * 头视图
     */
    protected View mHeaderView;
    /**
     * 尾视图
     */
    protected View mFooterView;
    protected BaseHolder<T> mHeaderHolder;
    protected BaseHolder<T> mFooterHolder;

    public BaseAdapter(Context context, List<T> models) {
        this.mContext = context;
        this.mModels = models;
        relations = initRelations();
    }

    protected abstract BaseRelations<T> initRelations();

    /**
     * 从Relations获取对应IModel的ItemViewType类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BaseRelations.HEADER_VIEW;
        } else if (position == getItemCount() - 1) {
            return BaseRelations.FOOTER_VIEW;
        } else {
            return relations.getModelType(mModels.get(checkPosition(position)));
        }
    }

    /**
     * 通过Holder创建工厂生产viewType对应的Holder
     */
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder<T> holder = null;
        switch (viewType) {
            case BaseRelations.HEADER_VIEW:
                holder = getHeaderHolder(parent);
                break;
            case BaseRelations.FOOTER_VIEW:
                holder = getFooterHolder(parent);
                break;
            default:
                holder = HolderFactory.createHolder(mContext, relations, parent, viewType);
                break;
        }
        if (holder == null) {
            L.e(L.HOLDER, "Holder is null and view type is " + viewType);
            return null;
        }
        holder.setOnOperationListener(mListener);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (position == 0) {
            holder.onBindNull(position);
        } else if (position == getItemCount() - 1) {
            holder.onBindNull(position);
        } else {
            holder.onBindHolder(mModels.get(checkPosition(position)), checkPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        return mModels.size() + 2;
    }

    public void setOnOperationListener(OnOperationListener<T> listener) {
        this.mListener = listener;
    }

    /**
     * 添加头视图
     */
    public void addHeaderView(View view) {
        this.mHeaderView = view;
    }

    public void addHeaderHolder(BaseHolder<T> holder) {
        this.mHeaderHolder = holder;
    }

    /**
     * 添加尾视图
     */
    public void addFooterView(View view) {
        this.mFooterView = view;
    }

    public void addFooterHolder(BaseHolder<T> holder) {
        this.mFooterHolder = holder;
    }

    /**
     * 获取头视图Holder
     */
    protected BaseHolder<T> getHeaderHolder(ViewGroup parent) {
        if (mHeaderHolder == null) {
            mHeaderView = checkIsEmpty(parent, mHeaderView);
            mHeaderHolder = new BaseHolder<T>(mHeaderView) {
                @Override
                protected void initChildren(View itemView) {
                }

                @Override
                public void onBindHolder(Object model, int position) {
                }
            };
        }
        return mHeaderHolder;
    }

    /**
     * 获取尾视图Holder
     */
    protected BaseHolder<T> getFooterHolder(ViewGroup parent) {
        if (mFooterHolder == null) {
            mFooterView = checkIsEmpty(parent, mFooterView);
            mFooterHolder = new BaseHolder<T>(mFooterView) {
                @Override
                protected void initChildren(View itemView) {
                }

                @Override
                public void onBindHolder(Object model, int position) {
                }
            };
        }
        return mFooterHolder;
    }

    /**
     * 校验头尾视图是否为null，若为null补充空白0*0 view
     */
    protected View checkIsEmpty(ViewGroup parent, View view) {
        if (view == null) {
            view = new View(parent.getContext());
        }
        return view;
    }

    @Override
    public void onViewAttachedToWindow(BaseHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder != null) {
            holder.onViewAttachedToWindow();
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null) {
            holder.onViewDetachedFromWindow();
        }
    }

    /**
     * 矫正Position
     */
    public int checkPosition(int position) {
        return position - 1;
    }

    public List<T> getModels() {
        return mModels;
    }

    public void notifyItemChanged(T t) {
        if (t == null) {
            return;
        }
        int index = getModels().indexOf(t);
        if (index > -1) {
            notifyItemChanged(index);
        }
    }
}
