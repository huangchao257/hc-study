package com.hc.design.observer.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类描述: 观察者行动
 *
 * @author: HuangChao
 * @since: 2023/07/14 17:38
 */
public class ObserverAction {

    /**
     * 观察者类
     */
    private Object target;
    /**
     * 订阅方法
     */
    private Method method;

    /**
     * 描述: 构造方法
     *
     * @param target 观察者类
     * @param method 方法
     * @author: HuangChao
     * @since: 2023/7/14 17:42
     */
    public ObserverAction(Object target, Method method) {
        if (target == null) {
            throw new NullPointerException();
        } else {
            this.target = target;
        }
        this.method = method;
        this.method.setAccessible(true);
    }

    /**
     * 描述: 执行指定的订阅方法
     *  event是method方法的参数
     * @param event
     * @author: HuangChao
     * @since: 2023/7/14 17:41
     */
    public void execute(Object event) {
        try {
            method.invoke(target, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}