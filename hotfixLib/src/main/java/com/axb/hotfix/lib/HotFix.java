package com.axb.hotfix.lib;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.axb.hotfix.reflect.lib.ShareReflectUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HotFix {
    /**
     * 安装补丁包（热修复）
     * 目前支持android 28版本（android p），其他版本未适配
     * @param application application
     * @param patchFile 补丁包全路径
     */
    public static void installPatch(Application application, File patchFile){
        //1、获取当前应用的PathClassLoader
        ClassLoader classLoader = application.getClassLoader();
        try {
            //2、反射拿到DexPathList属性对象pathList
            Field pathListField = ShareReflectUtil.findField(classLoader,"pathList");
            Object pathList = pathListField.get(classLoader);

            //3、反射修改pathList中的dexElements
            //3.1、把补丁包patch.dex转换为Elements[]
            List<File> files = new ArrayList<>();
            files.add(patchFile);

            File dexOutputDir = application.getCacheDir();

            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Method makePathElements = ShareReflectUtil.findMethod(pathList,
                    "makePathElements",
                    List.class,
                    File.class,
                    List.class);
            //如果是静态方法，第一个参数可以是null
            Object[] pathElements = (Object[]) makePathElements.invoke(null,files,dexOutputDir,suppressedExceptions);

            //3.2、获得pathList的dexElements属性(old)
            Field dexElementsField = ShareReflectUtil.findField(pathList,"dexElements");
            Object[] dexElements = (Object[]) dexElementsField.get(pathList);

            //3.3、patch+old合并，并反射赋值给pathList的dexElements
            Object[] newElements = (Object[]) Array.newInstance(pathElements.getClass().getComponentType(),pathElements.length+dexElements.length);
            System.arraycopy(pathElements,0,newElements,0,pathElements.length);
            System.arraycopy(dexElements,0,newElements,pathElements.length,dexElements.length);

            dexElementsField.set(pathList,newElements);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
