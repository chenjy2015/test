package com.example.chenjy.myapplication.annotation.bean;

import com.example.chenjy.myapplication.annotation.Factory;

@Factory(id = "Female", type = People.class)
public class Female extends People {
    @Override
    public String getName() {
        return "女生";
    }

    @Override
    public int getAge() {
        return 26;
    }

    @Override
    public int getSex() {
        return 0;
    }
}
