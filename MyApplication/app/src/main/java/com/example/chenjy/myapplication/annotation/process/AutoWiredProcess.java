package com.example.chenjy.myapplication.annotation.process;

import com.example.chenjy.myapplication.annotation.AutoWired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * runtime 运行时注解解析 自动new对象
 */
public class AutoWiredProcess {

    public static void bind(final Object object) {
        Class parentClass = object.getClass();
        Field[] fields = parentClass.getDeclaredFields();
        for (final Field field : fields) {
            AutoWired autoWiredProcess = field.getAnnotation(AutoWired.class);
            if (autoWiredProcess != null) {
                field.setAccessible(true);
                Class<?> autoCreateClass = field.getType();
                try {
                    Constructor autoCreateConstructor = autoCreateClass.getConstructor();
                    field.set(object, autoCreateConstructor.newInstance());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
