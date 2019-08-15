package com.example.chenjy.myapplication.parallx_viewpager;


/**
 * @route:
 * @descript: 滑动方向
 * @create: chenjiayou
 * create at 2018/12/7 下午5:28
 */
public enum Mode {

    LEFT_OVERLAY(0), RIGHT_OVERLAY(1), NONE(2);
    int value;

    Mode(int value) {
        this.value = value;
    }
}
