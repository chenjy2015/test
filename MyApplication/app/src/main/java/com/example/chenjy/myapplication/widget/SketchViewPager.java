package com.example.chenjy.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.chenjy.myapplication.utils.L;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SketchViewPager extends ViewPager {
    public SketchViewPager(@NonNull Context context) {
        super(context);
    }

    public SketchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            L.e(e);
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            L.e(e);
            return false;
        }
    }
}
