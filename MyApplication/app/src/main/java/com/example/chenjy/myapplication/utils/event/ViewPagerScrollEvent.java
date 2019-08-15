package com.example.chenjy.myapplication.utils.event;

public class ViewPagerScrollEvent extends BusEvent {
    public int position;

    public ViewPagerScrollEvent(int position) {
        this.position = position;
    }

}
