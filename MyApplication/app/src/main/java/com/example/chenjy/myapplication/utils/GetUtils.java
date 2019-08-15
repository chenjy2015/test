package com.example.chenjy.myapplication.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GetUtils {

    public static <T> T get(Object object) {
        if (object == null) {
            throw new NullPointerException("param cannot be null!");
        }
        try {
            Type type = object.getClass().getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                return null;
            }
            Type[] params = ((ParameterizedType) type).getActualTypeArguments();
            Class<T> clazz = (Class<T>) params[0].getClass();
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
