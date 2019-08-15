package com.example.chenjy.myapplication.ui;

import android.app.Activity;
import android.content.Intent;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.binder.SortBinder;
import com.example.chenjy.myapplication.databinding.ActivitySortBinding;
import com.example.chenjy.myapplication.parser.CharacterParser;
import com.example.chenjy.myapplication.parser.PinyinComparator;
import com.example.chenjy.myapplication.parser.SortModel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import me.drakeet.multitype.MultiTypeAdapter;

public class SortActivity extends BaseUIActivity<ActivitySortBinding> {

    // 26个字母
    public static String[] ps = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    public static String[] ds = {
            "张三", "李四", "王五", "中山", "叶俊招", "余翰林", "黄思华", "陈振宇", "镇宝山",
            "留的话", "国福彩", "立马", "12马华峰", "水电费", "3123玩儿翁", "下支撑", "阿斯蒂芬Q", "好几款", "斯顿发我S", "海景房", "玩儿若", "婆婆",
            "平行线", "半年", "剖腹", "isad扶贫", "#阿斯蒂芬", "现网", "发大水"
    };

    List<SortModel> mSortList = new ArrayList<>();
    CharacterParser characterParser = CharacterParser.getInstance();

    MultiTypeAdapter adapter = new MultiTypeAdapter();

    public static void startActivity(Activity act, String[] ds) {
        Intent intent = new Intent(act, SortActivity.class);
        intent.putExtra("ds", ds);
        act.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sort;
    }

    @Override
    public void initView() {
        adapter.register(SortModel.class, new SortBinder());
        adapter.setItems(mSortList);
        mViewDataBinding.recycler.setAdapter(adapter);
        mViewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void init() {
        super.init();
        String[] arrays = getIntent().getStringArrayExtra("ds");
        if (arrays != null) {
            ds = arrays;
        }
    }

    @Override
    public void initData() {
        for (int i = 0; i < ds.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(ds[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(ds[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        // 根据a-z进行排序源数据
        Collections.sort(mSortList, new PinyinComparator());
        adapter.notifyDataSetChanged();
    }


}
