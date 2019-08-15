package com.example.chenjy.myapplication.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.binder.SelectBinder;
import com.example.chenjy.myapplication.SelectVO;
import com.example.chenjy.myapplication.binder.SelectedBinder;
import com.example.chenjy.myapplication.ViewUtils;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityMultiSelectBinding;
import com.jakewharton.rxbinding2.view.RxView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @route:
 * @descript: 测试多选界面 全选与反选的速度
 * @create: chenjiayou
 * create at 2019/4/11 下午5:02
 */
public class MultiSelectActivity extends BaseUIActivity<ActivityMultiSelectBinding> implements ViewUtils.OnScrollListener {

    private static final String TAG = "MultiSelectActivity";
    private static final int TOTAL = 50000;
    private int mPageIndex = 1;
    private int mPageSize = 50000;

    private List<SelectVO> mDatas = new ArrayList<>();

    private List<SelectVO> mSelectedVOList = new ArrayList<>();

    private MultiTypeAdapter mSelectedAdapter;

    private MultiTypeAdapter mAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_multi_select;
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
        registerScrollChangeListener(mViewDataBinding.recycler, this);

        RxView.clicks(mViewDataBinding.selectAll)
                .throttleLast(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> toggleSelect(true));

        RxView.clicks(mViewDataBinding.selectNone)
                .throttleLast(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> toggleSelect(false));

        RxView.clicks(mViewDataBinding.clear)
                .throttleLast(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    mPageIndex = 0;
                    mDatas.clear();
                    mAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void initData() {
        load();
    }

    public void load() {
        int index = mDatas.size();
//        Observable.create(e -> {
        for (int i = mDatas.size(); i < mPageIndex * mPageSize && mPageIndex * mPageSize <= TOTAL; i++) {
            SelectVO selectVO = new SelectVO("item " + i, false, "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg");
            mDatas.add(selectVO);
        }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o ->
//                        mAdapter.notifyItemRangeInserted(index, mDatas.size()));
        mAdapter.notifyItemRangeInserted(index, mDatas.size());

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
        mAdapter.notifyItemChanged(position);
    }

    public void onClick(int position) {
        //notify bottom list
        SelectVO selectVO = mSelectedVOList.remove(position);
        mSelectedAdapter.notifyItemRemoved(position);
        mSelectedAdapter.notifyItemRangeRemoved(position, mSelectedVOList.size() - position);

        //notify main list
        int index = mDatas.indexOf(selectVO);
        if (index != -1) {
            SelectVO selectVO1 = mDatas.get(index);
            selectVO1.isSelect = false;
            mAdapter.notifyItemChanged(index);
        }
    }

    @Override
    public void onScrollBottom() {
        if (mPageIndex * mPageSize == TOTAL) {
            showToast("没有更多了");
        } else {
            mPageIndex++;
            load();
        }
    }

    @Override
    public void onScrollTop() {

    }

    @Override
    public void onVisibleItemChange(int start, int end) {
        Log.d(TAG, "start: " + start + "   end: " + end);
    }

    public void toggleSelect(boolean select) {
        //notify bottom list
        int size = mSelectedVOList.size();
        mSelectedVOList.clear();
        mSelectedAdapter.notifyItemRangeRemoved(0, size);
        //notify main list
        for (int i = 0; i < mDatas.size(); i++) {
            SelectVO selectVO = mDatas.get(i);
            selectVO.isSelect = select;
            if (select) {
                mSelectedVOList.add(selectVO);
            }
        }
        mAdapter.notifyItemRangeChanged(0, mDatas.size());
        //notify bottom list
        if (select) {
            mSelectedAdapter.notifyItemRangeInserted(0, size);
        }
    }
}
