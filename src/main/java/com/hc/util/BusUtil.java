package com.hc.util;

import com.alibaba.fastjson2.JSON;
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
     * @param r           赋值对象
     * @param setterSet   需要赋值的字段set方法名称
     * @param clazz       需要赋值的类型
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
     * @param r           赋值对象
     * @param clazz       需要赋值的类型
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


    /**
     * 描述: Java to string 输出转 JSON
     *
     * @param javaEntityStr
     * @return {@link String}
     * @author: HuangChao
     * @since: 2025/6/4
     */
    public static String javaEntityStrToJsonStr(String javaEntityStr) {
        // 去除首尾的括号和空格
        String content = javaEntityStr.trim().replaceAll("[()]", "");

        // 构建 Map 存储键值对
        Map<String, Object> map = new LinkedHashMap<>();

        // 拆分为键值对
        for (String pair : content.split(", ")) {
            if (pair.contains("=")) {
                String[] entry = pair.split("=", 2); // 避免 value 中含有 '=' 导致错误拆分
                String key = entry[0];
                String value = entry.length > 1 ? entry[1] : null;

                // 处理 null、数字、字符串等基础类型
                Object convertedValue = convertValue(value);
                map.put(key, convertedValue);
            }
        }

        // 使用 fastjson2 转换为 JSON 字符串
        return JSON.toJSONString(map);
    }

    /**
     * 描述: 简单类型转换，将字符串转为对应的基础类型
     */
    private static Object convertValue(String value) {
        if (value == null || "null".equals(value)) {
            return null;
        } else if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.valueOf(value);
        } else if (value.matches("-?\\d+")) { // 判断是否为整数
            return Integer.valueOf(value);
        } else if (value.matches("-?\\d+\\.\\d+")) { // 判断是否为浮点数
            return Double.valueOf(value);
        } else {
            return value; // 保留字符串
        }
    }



    public static void main(String[] args) {
        String javaEntityStr = "(spId=null, spIds=null, mobile=null, plat=10, type=null, name=null, userMobile=null, userName=null, fullName=null, provinceId=null, cityId=null, countyId=null, provinceIds=null, cityIds=null, countyIds=null, subArea=null, subAreas=null, status=2, timeRange=null, startTime=null, endTime=null, plats=null, cooperationPlatform=null, serviceType=null, useProduct=null, cooperativeBrand=null, certifications=null, spServiceType=null, unionPaySignStatus=null, firstOrgId=null, secondOrgId=null, thirdOrgId=null, subCompanyId=null, subCompanyIdList=null, twoType=null, thirdType=null, property=null, ruleAreaProvinceId=null, ruleAreaCityId=null, ruleAreaCountyId=null, ruleServCategId=null, ruleCategOneId=null, ruleCategTwoId=null, statementCycle=null, bizType=null, checkSpManagerId=null, permitFirstOrgIdList=null, permitSecondOrgIdList=null, permitThirdOrgIdList=null, permitSpIdList=null, permitProvinceIdList=null, permitCityIdList=null, permitCountyIdList=null)";
        String jsonStr = javaEntityStrToJsonStr(javaEntityStr);
        System.out.println(jsonStr);
    }
}
