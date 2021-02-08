package com.axb.hotfix.reflect.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * java反射相关工具类
 */
public class ShareReflectUtil {
    /**
     * 反射获得对象中的属性
     * @param instance 对象
     * @param name 属性名称
     * @return
     */
    public static Field findField(Object instance,String name){
        Class<?> cls = instance.getClass();
        while(cls != Object.class){
            try {
                Field field = cls.getDeclaredField(name);
                if(field!=null){
                    //赋予权限，主要用于给私有属性赋予可访问权限
                    field.setAccessible(true);
                    return field;
                }
            } catch (NoSuchFieldException e) {
            }
            //如果获取不到，可能属性的在父类
            cls = cls.getSuperclass();
        }

        throw new RuntimeException(name+" not found in "+instance.getClass().getName());
    }
    /**
     * 反射获得对象中的方法
     * @param instance 对象
     * @param name 方法名称
     * @param parameterTypes 参数类型
     * @return
     */
    public static Method findMethod(Object instance,String name,Class<?>... parameterTypes){
        Class<?> cls = instance.getClass();
        while(cls != Object.class){
            try {
                Method method = cls.getDeclaredMethod(name,parameterTypes);
                if(method!=null){
                    //赋予权限，主要用于给私有属性赋予可访问权限
                    method.setAccessible(true);
                    return method;
                }
            } catch (NoSuchMethodException e) {
            }
            //如果获取不到，可能属性的在父类
            cls = cls.getSuperclass();
        }

        throw new RuntimeException(name+" not found in "+instance.getClass().getName());
    }
}