package com.example.chenjy.myapplication;


import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.DrawableRes;

/**
 * Create by Melorin on 2018/12/10
 */
public class ChatEmojiBean {
    //菜单底部position。
    public static final int BOTTOM_POSITION_EMOJI_NET = 2;

    //type.
    public static final int DELETE = 0;
    public static final int BLANK = 1;
    public static final int EMOJI = 2;
    public static final int EMOJI_CUSTOM = 3;
    public static final int EMOJI_CUSTOM_NET = 4;
    public static final int EDIT = 5;
    public static final int ADD = 6;

    //action.
    public static final int ACTION_CLICK = 7;
    public static final int ACTION_LONG_CLICK = 8;
    public static final int ACTION_UP = 9;

    private int type;
    private String label;
    private String path;
    @DrawableRes
    private int resource;

    private String key;

    public static Map<String, Integer> nameEmojiMap = new LinkedHashMap<>();
    public static Map<String, Integer> nameEmojiCustomMap = new LinkedHashMap<>();

    public static int find(String label) {
        return nameEmojiMap.containsKey(label) ? nameEmojiMap.get(label) : -1;
    }

    public static int findEmojiCustom(String label) {
        return nameEmojiCustomMap.containsKey(label) ? nameEmojiCustomMap.get(label) : -1;
    }

    public ChatEmojiBean(int type, String label, String path, int resource) {
        this.type = type;
        this.label = label;
        this.path = path;
        this.resource = resource;
    }

    public ChatEmojiBean(int type, String label, String path, int resource, String key) {
        this.type = type;
        this.label = label;
        this.path = path;
        this.resource = resource;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public String getPath() {
        return path;
    }

    public int getResource() {
        return resource;
    }

    public int getType() {
        return type;
    }

}
