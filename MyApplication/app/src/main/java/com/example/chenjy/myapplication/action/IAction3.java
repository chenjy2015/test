package com.example.chenjy.myapplication.action;

/**
 * Created by melorin on 2017/11/14.
 */
public interface IAction3<T, R, V> {

    void invoke(T t, R r, V v);
}
