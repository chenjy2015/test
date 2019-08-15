package com.example.chenjy.myapplication.ui;

import com.blankj.utilcode.util.ResourceUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.binder.SketchBinder;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivitySketchListBinding;
import com.example.chenjy.myapplication.sketch.ImageType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import me.drakeet.multitype.MultiTypeAdapter;
import me.panpf.sketch.sample.bean.UnsplashImage;

public class SketchListActivity extends BaseUIActivity<ActivitySketchListBinding> {

    List<UnsplashImage> mImgList = new ArrayList<>();
    MultiTypeAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_sketch_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        String content = ResourceUtils.readAssets2String("resource.txt");
        Gson gson = new Gson();
        mImgList = gson.fromJson(content, new TypeToken<ArrayList<UnsplashImage>>() {
        }.getType());
        if (mImgList == null) {
            mImgList = new ArrayList<>();
        }
        mAdapter = new MultiTypeAdapter();

        mAdapter.register(
                UnsplashImage.class,
                new SketchBinder(ImageType.IMAGE_TYPE_NORMAL,
                        position -> startActivity(DetailsDisplayActivity.launch(this, position, new Gson().toJson(mImgList))))
        );
        mAdapter.setItems(mImgList);
        mViewDataBinding.recycler.setAdapter(mAdapter);
        mViewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
