package com.example.chenjy.myapplication;


import java.util.List;

import androidx.annotation.DrawableRes;

/**
 * Create by Melorin on 2018/12/10
 */
public class ChatEmojiPageBean {

    private String label;
    private List<ChatEmojiBean> emojiList;
    @DrawableRes
    private int labelDrawable;
    private int line;
    private int row;
    private int type;

    public ChatEmojiPageBean(String label, int labelDrawable, List<ChatEmojiBean> emojiList, int line, int row, int type) {
        this.label = label;
        this.labelDrawable = labelDrawable;
        this.emojiList = emojiList;
        this.line = line;
        this.row = row;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public int getLabelDrawable() {
        return labelDrawable;
    }

    public List<ChatEmojiBean> getEmojiList() {
        return emojiList;
    }

    public void setEmojiList( List<ChatEmojiBean> emojiList) {
        this.emojiList = emojiList;
    }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public int getType() {
        return type;
    }

    public int calculatePages() {
        int size;
        if (!ValidUtils.isValid(emojiList)) {
            size = 0;
        } else if (emojiList.get(0).getType() == ChatEmojiBean.EMOJI) {
            size = (emojiList.size() - 1) / (row * line - 1) + 1;
        } else {
            size = (emojiList.size() - 1) / (row * line) + 1;
        }
        return size;
    }
}
