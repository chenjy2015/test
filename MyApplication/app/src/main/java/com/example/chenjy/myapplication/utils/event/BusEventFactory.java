package com.example.chenjy.myapplication.utils.event;


public class BusEventFactory {

    public static BusEvent invokeEvent(int position) {
        return new ViewPagerScrollEvent(position);
    }
}
