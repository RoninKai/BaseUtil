package com.tanker.base.util.inject;

import android.app.Activity;
import android.view.View;

import com.tanker.base.util.ClickProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/11/14
 * @describe : 重复点击注解初始化类
 */
public class ViewInjectHelper {


    public static void init(final Activity activity) {
        Class classObj = activity.getClass();
        Field[] fields = classObj.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(OnclickOne.class)){
                OnclickOne onClickView = field.getAnnotation(OnclickOne.class);
                final int viewId = onClickView.value();
                int time = onClickView.time();
                String funcName = onClickView.onClick();
                try {
                    final Method method = classObj.getMethod("findViewById", int.class);
                    final Object viewObj = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, viewObj);
                    final Method methodClick = classObj.getMethod(funcName, View.class);
                    ((View)viewObj).setOnClickListener(new ClickProxy(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                methodClick.invoke(activity, viewObj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    },time));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}