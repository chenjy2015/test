package com.example.chenjy.myapplication.sketch;

/**
 * Created by chenjy on 2019/4/25.
 */

public enum ImageType {

    IMAGE_TYPE_NORMAL(0),
    IMAGE_TYPE_LARGE(1);

    private int type;

    ImageType(int type) {
        this.type = type;
    }
}
