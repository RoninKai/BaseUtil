package com.tanker.base.util.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/11/14
 * @describe : 点击事件注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnclickOne {
    int value();

    int time() default 1000;

    String onClick() default "";
}
