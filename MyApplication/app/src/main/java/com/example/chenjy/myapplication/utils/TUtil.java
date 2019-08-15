package com.example.chenjy.myapplication.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by melorin on 2017/10/27.
 */
public class TUtil {

    public static <T> T get(Object obj) {
        try {
            Type genType = obj.getClass().getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                return null;
            }
            //返回表示此类型实际类型参数的 Type 对象的数组。
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Class<T> clazz = (Class<T>) params[0];
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
