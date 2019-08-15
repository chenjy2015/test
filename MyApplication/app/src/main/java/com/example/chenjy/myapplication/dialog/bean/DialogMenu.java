package com.example.chenjy.myapplication.dialog.bean;


import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * Create by Melorin on 2018/12/27
 */
public class DialogMenu {

    private int id;
    @ColorInt
    private int color;
    @DrawableRes
    private int drawableRes;

    private String item;
    private String extAttr;
    public DialogMenu(int id, String item) {
        this(id, 0, item);
    }

    public DialogMenu(int id, int color, String item) {
        this.id = id;
        this.color = color;
        this.item = item;
    }

    public DialogMenu(int id, String item,String extAttr) {
        this.id = id;
        this.item = item;
        this.extAttr = extAttr;
    }

    public DialogMenu(int id, int color, int drawableRes, String item) {
        this.id = id;
        this.color = color;
        this.drawableRes = drawableRes;
        this.item = item;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getItem() {
        return item;
    }

    public String getExtAttr() {
        return extAttr;
    }
}
