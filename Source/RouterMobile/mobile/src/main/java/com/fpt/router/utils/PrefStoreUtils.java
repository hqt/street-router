package com.fpt.router.utils;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Using this class for performance. put it just apply for API >= 11
 * Created by huynh quang thao on 9/19/15.
 */
public class PrefStoreUtils {

    private static final Method sApplyMethod = prefApplyMethod();

    private static Method prefApplyMethod() {
        try{
            Class cls = SharedPreferences.Editor.class;
            return cls.getMethod("apply");
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    public static void apply(SharedPreferences.Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor);
                return;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        editor.commit();
    }

}
