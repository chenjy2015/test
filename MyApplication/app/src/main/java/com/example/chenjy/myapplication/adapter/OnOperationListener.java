package com.example.chenjy.myapplication.adapter;

import android.view.View;

/**
 * View操作监听器
 * Created by melorin on 2017/1/28.
 */
public interface OnOperationListener<T> {

    void onOperation(int type, int position, View view, T model);
}