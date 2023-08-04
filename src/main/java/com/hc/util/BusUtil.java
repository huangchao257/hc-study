package com.hc.util;

import org.springframework.util.ReflectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 类描述: 公共工具类
 * ps：通用方法工具类，记录不足抽取成独立工具类的公共方法
 *
 * @author: HuangChao
 * @since: 2023/04/10 14:17
 */
public class BusUtil {

    /**
     * 描述: 批量处理
     *
     * @param maxNum     单次最大处理数量
     * @param sourceList 数据来源集合
     * @param handler    处理函数
     * @return {@link List<R>} 分批处理汇总数据集合
     * @author: HuangChao
     * @since: 2023/4/10 14:23
     */
    public static <T, R> List<R> batchHandle(int maxNum, List<T> sourceList, Function<List<T>, List<R>> handler) {
        final List<R> results = new ArrayList<>(sourceList.size());
        final int maxSourceIdx = sourceList.size();
        int sIdx = 0;
        int eIdx;
        do {
            eIdx = Math.min(sIdx + maxNum, maxSourceIdx);
            final List<R> dataList = handler.apply(sourceList.subList(sIdx, eIdx));
            if (dataList != null && !dataList.isEmpty()) {
                results.addAll(dataList);
            }
        } while ((sIdx = eIdx) < maxSourceIdx);
        return results;
    }

    /**
     * 描述: 通过反射设置对象指定参数默认值
     *
     * @param r 赋值对象
     * @param setterSet 需要赋值的字段set方法名称
     * @param clazz 需要赋值的类型
     * @param defaultVale 默认值
     * @author: HuangChao
     * @since: 2023/4/17 11:22
     */
    public static <T, R, P> void setObjectDefault(R r, Set<String> setterSet, Class<T> clazz, P defaultVale) {
        ReflectionUtils.doWithMethods(r.getClass(), method -> {
            ReflectionUtils.invokeMethod(method, r, defaultVale);
        }, method -> {
            String name = method.getName();
            return setterSet.contains(name) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == clazz;
        });
    }

    /**
     * 描述: 通过反射设置对象指定类型参数默认值
     *
     * @param r 赋值对象
     * @param clazz 需要赋值的类型
     * @param defaultVale 默认值
     * @author: HuangChao
     * @since: 2023/4/17 11:23
     */
    public static <T, R, P> void setObjectDefault(R r, Class<T> clazz, P defaultVale) {
        ReflectionUtils.doWithMethods(r.getClass(), method -> ReflectionUtils.invokeMethod(method, r, defaultVale), method -> method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == clazz);
    }


    /**
     * 描述: 根据某个对象属性进行去重
     *
     * @param keyExtractor
     * @return {@link Predicate<T>}
     * @author: HuangChao
     * @since: 2023/5/23 15:28
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(16);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 描述: 根据多个对象属性进行去重
     *
     * @param keyExtractors
     * @return {@link Predicate<T>}
     * @author: HuangChao
     * @since: 2023/5/23 15:19
     */
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        Map<List<?>, Boolean> seen = new ConcurrentHashMap<>(16);
        return t -> {
            List<?> keys = Arrays.stream(keyExtractors)
                    .map(keyExtractor -> keyExtractor.apply(t))
                    .collect(Collectors.toList());
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

}
