package com.hc.design.observer.common;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 类描述: 观察者注册中心
 *
 * @author: HuangChao
 * @since: 2023/07/14 17:46
 */

public class ObserverRegistry {

    /**
     * 注册Map
     * 使用一个 HashMap，来记录每个事件的多个观察者。观察者是对每个 @Subscribe 注解对应方法的一个封装
     */
    private ConcurrentMap<Class<?>, CopyOnWriteArraySet<ObserverAction>> registry = new ConcurrentHashMap<>();

    /**
     * 描述: 注册方法
     *
     * @param observer
     * @author: HuangChao
     * @since: 2023/7/14 18:30
     */
    public void register(Object observer) {
        // 找到对应 listener 类的所有 Subscirbes，也就是所有包含 @Subscribe 注解的方法。
        Map<Class<?>, Collection<ObserverAction>> observerActions = findAllObserverActions(observer);
        // 从 listenerMethods 中，取出每个 Subscriber，注册到 eventSubscribers 属性中去
        for (Map.Entry<Class<?>, Collection<ObserverAction>> entry : observerActions.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> eventActions = entry.getValue();
            CopyOnWriteArraySet<ObserverAction> registeredEventActions = registry.get(eventType);
            if (registeredEventActions == null) {
                registry.putIfAbsent(eventType, new CopyOnWriteArraySet<>());
                registeredEventActions = registry.get(eventType);
            }
            registeredEventActions.addAll(eventActions);
        }
    }

    /**
     * 描述: 根据事件获取对应的观察者列表
     *
     * @param event
     * @return {@link List< ObserverAction>}
     * @author: HuangChao
     * @since: 2023/7/14 18:38
     */
    public List<ObserverAction> getMatchedObserverActions(Object event) {
        List<ObserverAction> matchedObservers = new ArrayList<>();
        Class<?> postedEventType = event.getClass();
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : registry.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> eventActions = entry.getValue();
            if (postedEventType.isAssignableFrom(eventType)) {
                matchedObservers.addAll(eventActions);
            }
        }
        return matchedObservers;
    }

    /**
     * 描述: 获取所有的订阅者
     *
     * @param observer
     * @return {@link Map< Class<?>,java.util.Collection<com.hc.design.observer.common.ObserverAction>>}
     * @author: HuangChao
     * @since: 2023/7/14 18:30
     */
    private Map<Class<?>, Collection<ObserverAction>> findAllObserverActions(Object observer) {
        Map<Class<?>, Collection<ObserverAction>> observerActions = new HashMap<>();
        Class<?> clazz = observer.getClass();

        // 去查找 listener 类对应的 @Subscribe 注解的方法
        for (Method method : getAnnotatedMethods(clazz)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> eventType = parameterTypes[0];
            if (!observerActions.containsKey(eventType)) {
                observerActions.put(eventType, new ArrayList<>());
            }
            observerActions.get(eventType).add(new ObserverAction(observer, method));
        }
        return observerActions;
    }

    /**
     * 描述: 获取存在订阅注解的方法
     *
     * @param clazz
     * @return {@link List< Method>}
     * @author: HuangChao
     * @since: 2023/7/14 18:31
     */
    private List<Method> getAnnotatedMethods(Class<?> clazz) {
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException(String.format("Method %s has @Subscribe annotation but has %s parameters."
                                    + "Subscriber methods must have exactly 1 parameter.",
                            method, parameterTypes.length));
                }
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }
}
