package com.hc.design.observer.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类描述: 订阅注解
 *  作用范围：方法
 *  作用时机：运行时
 *
 * @author: HuangChao
 * @since: 2023/07/14 17:34
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

}
