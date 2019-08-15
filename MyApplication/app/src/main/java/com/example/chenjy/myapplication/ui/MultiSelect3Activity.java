package com.example.chenjy.myapplication.ui;


import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.SelectVO;
import com.example.chenjy.myapplication.binder.SelectedBinder;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityMultiSelect3Binding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import me.drakeet.multitype.MultiTypeAdapter;

public class MultiSelect3Activity extends BaseUIActivity<ActivityMultiSelect3Binding> {

    private static final int TOTAL = 5000;

    private List<SelectVO> mDatas = new ArrayList<>();
    private MultiTypeAdapter mAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_multi_select3;
    }

    @Override
    public void initView() {
        mAdapter = new MultiTypeAdapter();
        mAdapter.setItems(mDatas);
        mAdapter.register(SelectVO.class, new SelectedBinder(this::onClick));
        mViewDataBinding.recycler.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewDataBinding.recycler.setLayoutManager(linearLayoutManager);
        mViewDataBinding.recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        ((SimpleItemAnimator) mViewDataBinding.recycler.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public void initEvent() {

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

    public void onClick(int position) {
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeRemoved(position, mDatas.size() - position);
        mViewUtils.smoothMoveToPosition(mViewDataBinding.recycler, mDatas.size() - 1);
    }
}
