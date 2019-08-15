package com.example.chenjy.myapplication.annotation.process;

import android.app.Activity;
import android.view.View;

import com.example.chenjy.myapplication.annotation.BindView;

import java.lang.reflect.Field;

/**
 * runtime 运行时注解解析 findview
 */
public class ButterKnifeProcess {

    public static void bind(Activity activity) {

        Field[] fields = activity.getClass().getDeclaredFields();

        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null && bindView.value() != 0) {
                field.setAccessible(true);
                View view = activity.findViewById(bindView.value());
                try {
                    if (view != null) {
                        field.set(activity, view);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
