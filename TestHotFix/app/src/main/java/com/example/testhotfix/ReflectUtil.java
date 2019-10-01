package com.example.testhotfix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

    public static Field getField(Object obj, String name) throws NoSuchFieldException {
        Class<?> clazz = obj.getClass();
        while (null != clazz) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field:" + name + " not found in " + obj.getClass());
    }

    public static Method getMethod(Object obj, String name, Class<?>... params) {
        Class<?> clazz = obj.getClass();
        while (null != clazz) {
            try {
                Method method = clazz.getDeclaredMethod(name, params);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}