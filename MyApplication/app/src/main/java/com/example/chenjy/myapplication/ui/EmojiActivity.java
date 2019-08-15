package com.example.chenjy.myapplication.ui;

import android.text.Spanned;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityEmojiBinding;
import com.example.chenjy.myapplication.utils.EmojiHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class EmojiActivity extends BaseUIActivity<ActivityEmojiBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_emoji;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        String emoji = "[/tp]";
        Spanned spanned = new EmojiHelper(this).flat(emoji);
        mViewDataBinding.emojiTv.setText(spanned);
    }

    @Override
    public void initData() {

    }

}
