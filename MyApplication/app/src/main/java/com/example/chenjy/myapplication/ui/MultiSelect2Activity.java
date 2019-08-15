package com.example.chenjy.myapplication.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.binder.SelectBinder;
import com.example.chenjy.myapplication.SelectVO;
import com.example.chenjy.myapplication.binder.SelectedBinder;
import com.example.chenjy.myapplication.ViewUtils;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityMultiSelect2Binding;
import com.jakewharton.rxbinding2.view.RxView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @route:
 * @descript: 测试多选界面 全选与反选的速度
 * @create: chenjiayou
 * create at 2019/4/11 下午5:02
 */
public class MultiSelect2Activity extends BaseUIActivity<ActivityMultiSelect2Binding> {

    private static final String TAG = "MultiSelectActivity";
    private static final int TOTAL = 5000;
    private int mPageIndex = 0;
    private int mPageSize = 10;

    private List<SelectVO> mDatas = new ArrayList<>();

    private List<SelectVO> mSelectedVOList = new ArrayList<>();

    private MultiTypeAdapter mSelectedAdapter;

    private MultiTypeAdapter mAdapter;

    private boolean isSelectAll; //全选和反选标识

    private boolean isBottomTouchMove; //底部选中列表 是否手指触摸滑动标识


    @Override
    public int getLayout() {
        return R.layout.activity_multi_select2;
    }

    @Override
    public void initView() {
        mAdapter = new MultiTypeAdapter();
        mAdapter.setItems(mDatas);
        mAdapter.register(SelectVO.class, new SelectBinder(this::onChange));
        mViewDataBinding.recycler.setAdapter(mAdapter);
        mViewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        ((SimpleItemAnimator) mViewDataBinding.recycler.getItemAnimator()).setSupportsChangeAnimations(false);

        mSelectedAdapter = new MultiTypeAdapter();
        mSelectedAdapter.setItems(mSelectedVOList);
        mSelectedAdapter.register(SelectVO.class, new SelectedBinder(this::onClick));
        mViewDataBinding.recyclerSelected.setAdapter(mSelectedAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewDataBinding.recyclerSelected.setLayoutManager(linearLayoutManager);
        mViewDataBinding.recyclerSelected.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        ((SimpleItemAnimator) mViewDataBinding.recyclerSelected.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initEvent() {
        RxView.clicks(mViewDataBinding.selectAll)
                .throttleLast(0, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> toggleSelectAll(true));

        RxView.clicks(mViewDataBinding.selectNone)
                .throttleLast(0, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> toggleSelectAll(false));

        RxView.clicks(mViewDataBinding.clear)
                .throttleLast(0, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    mPageIndex = 0;
                    mDatas.clear();
                    mAdapter.notifyDataSetChanged();
                });

        registerScrollChangeListener(mViewDataBinding.recycler, mainScrollListener);
        registerScrollChangeListener(mViewDataBinding.recyclerSelected, bottomScrollListener);

        mViewDataBinding.recyclerSelected.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                isBottomTouchMove = true;
            } else {
                isBottomTouchMove = false;
            }
            return false;
        });
    }

    @Override
    public void initData() {
        load();
    }

    public void load() {
        for (int i = 0; i < TOTAL; i++) {
            SelectVO selectVO = new SelectVO("item " + i, false, "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg");
            mDatas.add(selectVO);
        }
        mAdapter.notifyItemRangeInserted(0, mDatas.size());
    }

    public void onChange(boolean checked, int position) {
        SelectVO selectVO = mDatas.get(position);
        selectVO.isSelect = checked;
        if (checked) {
            int size = mSelectedVOList.size();
            mSelectedVOList.add(selectVO);
            mSelectedAdapter.notifyItemRangeInserted(size, mSelectedVOList.size());
        } else {
            int index = mSelectedVOList.indexOf(selectVO);
            mSelectedVOList.remove(selectVO);
            mSelectedAdapter.notifyItemRangeRemoved(index, mSelectedVOList.size());
        }
        mViewUtils.smoothMoveToPosition(mViewDataBinding.recyclerSelected, mSelectedVOList.size() - 1);
        mAdapter.notifyItemChanged(position);
    }

    public void onClick(int position) {
        //notify bottom list
        SelectVO selectVO = mSelectedVOList.remove(position);
        mSelectedAdapter.notifyItemRemoved(position);
        mSelectedAdapter.notifyItemRangeRemoved(position, mSelectedVOList.size() - position);
        mViewUtils.smoothMoveToPosition(mViewDataBinding.recyclerSelected, mSelectedVOList.size() - 1);
        //notify main list

        int index = mDatas.indexOf(selectVO);
        if (index != -1) {
            SelectVO selectVO1 = mDatas.get(index);
            selectVO1.isSelect = false;
            mAdapter.notifyItemChanged(index);
        }
    }

    public void toggleSelectAll(boolean isSelect) {
        isSelectAll = isSelect;
        reset();
        toggleSelect(0, mDatas.size(), isSelect);
        if (!isSelect) {
            int size = mSelectedVOList.size();
            mSelectedVOList.clear();
            mSelectedAdapter.notifyItemRangeRemoved(0, size);
            mViewUtils.smoothMoveToPosition(mViewDataBinding.recyclerSelected, mSelectedVOList.size() - 1);
        }
    }

    public void toggleSelect(int start, int end, boolean select) {
        //notify main list
        Set<SelectVO> set = new HashSet<>();
        set.addAll(mSelectedVOList);
        for (int i = start; i < end; i++) {
            SelectVO selectVO = mDatas.get(i);
            //如果此区间数据已经更新 则无需再notify 提高效率
            if (selectVO.isSelect != select) {
                selectVO.isSelect = select;
//                mAdapter.notifyItemChanged(i);
            }
            if (select) {
                if (set.add(selectVO)) {
                    mSelectedVOList.add(selectVO);
//                    mSelectedAdapter.notifyItemInserted(mSelectedVOList.size());
                }
            }
        }
        mAdapter.notifyItemRangeChanged(start, end);
        if (select) {
            mSelectedAdapter.notifyItemRangeInserted(start, end);
            mViewUtils.smoothMoveToPosition(mViewDataBinding.recyclerSelected, mSelectedVOList.size() - 1);
        }
    }

    /**
     * 全选与反选 底部list
     *
     * @param isSelect
     */
    public void toggleBottomSelectAll(boolean isSelect) {
        if (isSelect) {
            addBottomSelect(0, mDatas.size());
        } else {
            int size = mSelectedVOList.size();
            mSelectedVOList.clear();
            mSelectedAdapter.notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 添加item 底部list
     *
     * @param start
     * @param end
     */
    public void addBottomSelect(int start, int end) {
        //利用set 不可重复的特性做个去重复判断
        Set<SelectVO> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            if (i == mDatas.size()) {
                break;
            }
            SelectVO selectVO = mDatas.get(i);
            if (set.add(selectVO)) {
                mSelectedVOList.add(selectVO);
            }
        }
        mSelectedAdapter.notifyItemRangeInserted(start, mSelectedVOList.size());
        mViewUtils.smoothMoveToPosition(mViewDataBinding.recyclerSelected, mSelectedVOList.size() - 1);
    }

    /**
     * 全选或反选的时候 没有滑动recyclerview 这里手动触发获取当前可见item索引位置
     */
    public void activeTrigger(RecyclerView recyclerView, ViewUtils.SimpleScrollListener simpleScrollListener) {
        getVisibleIndexs(recyclerView, simpleScrollListener);
    }

    public void reset() {
        mPageIndex = 0;
        mPageSize = 10;
    }


    /**
     * 主列表
     */
    private MainScrollListener mainScrollListener = new MainScrollListener();

    public class MainScrollListener extends ViewUtils.SimpleScrollListener {

        @Override
        public void onScrollBottom() {
            super.onScrollBottom();
            if (mDatas.size() == TOTAL) {
                showToast("没有更多了");
            } else {
                mPageIndex++;
                load();
            }
        }

        @Override
        public void onVisibleItemChange(int start, int end) {
            Log.d(TAG, "start: " + start + "   end: " + end);
//            toggleSelect(start, end, isSelectAll);
        }
    }

    /**
     * 底部选中列表
     */
    private BottomScrollListener bottomScrollListener = new BottomScrollListener();

    public class BottomScrollListener extends ViewUtils.SimpleScrollListener {

        @Override
        public void onScrollBottom() {
            super.onScrollBottom();
            addBottomSelect(mSelectedVOList.size(), mPageSize);
        }
    }
}
