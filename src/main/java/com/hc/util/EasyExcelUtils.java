package com.hc.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hc.constant.GlobalConsts;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 类描述: easyexcel工具类
 *
 * @author: HuangChao
 * @since: 2023/07/31 14:18
 */
public class EasyExcelUtils<T> {

    /**
     * 描述: 获取读取Excel的监听器对象
     * 为了解耦及减少每个数据模型bean都要创建一个监听器的臃肿, 使用泛型指定数据模型类型
     * 使用jdk8新特性中的函数式接口 Consumer
     * 可以实现任何数据模型bean的数据解析, 不用重复定义监听器
     *
     * @param consumer  处理解析数据的函数, 一般可以是数据入库逻辑的函数
     * @param threshold 阈值,达到阈值就处理一次存储的数据
     * @param <T>       数据模型泛型
     * @return {@link AnalysisEventListener<T>} 返回监听器
     * @author: HuangChao
     * @since: 2023/7/31 15:52
     */
    public static <T> AnalysisEventListener<T> getReadListener(Consumer<List<T>> consumer, int threshold) {

        return new AnalysisEventListener<T>() {

            /**
             * 存储解析的数据 T t
             */
            // ArrayList基于数组实现, 查询更快
//            List<T> dataList = new ArrayList<>(threshold);
            // LinkedList基于双向链表实现, 插入和删除更快
            List<T> dataList = new LinkedList<>();

            /**
             * 每解析一行数据事件调度中心都会通知到这个方法, 订阅者1
             * @param data 解析的每行数据
             * @param context
             */
            @Override
            public void invoke(T data, AnalysisContext context) {
                dataList.add(data);
                // 达到阈值就处理一次存储的数据
                if (dataList.size() >= threshold) {
                    consumer.accept(dataList);
                    dataList.clear();
                }
            }

            /**
             * excel文件解析完成后,事件调度中心会通知到该方法, 订阅者2
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 最后阈值外的数据做处理
                if (dataList.size() > 0) {
                    consumer.accept(dataList);
                }
            }
        };

    }

    /**
     * 描述: 获取读取Excel的监听器对象
     * 为了解耦及减少每个数据模型bean都要创建一个监听器的臃肿, 使用泛型指定数据模型类型
     * 使用jdk8新特性中的函数式接口 Consumer
     * 可以实现任何数据模型bean的数据解析, 不用重复定义监听器
     *
     * @param consumer  处理解析数据的函数, 一般可以是数据入库逻辑的函数
     * @param function  转换数据的函数, 一般是类型转换的函数
     * @param threshold 阈值,达到阈值就处理一次存储的数据
     * @param <T,R>     数据模型泛型
     * @return {@link AnalysisEventListener<T>} 返回监听器
     * @author: HuangChao
     * @since: 2023/7/31 16:48
     */
    public static <T, R> AnalysisEventListener<T> getReadListener(Function<T, List<R>> function, Consumer<List<R>> consumer, int threshold) {

        return new AnalysisEventListener<T>() {

            /**
             * 存储解析的数据 R
             */
            // ArrayList基于数组实现, 查询更快
//            List<R> dataList = new ArrayList<>(threshold);
            // LinkedList基于双向链表实现, 插入和删除更快
            List<R> dataList = new LinkedList<>();

            /**
             * 每解析一行数据事件调度中心都会通知到这个方法, 订阅者1
             * @param data 解析的每行数据
             * @param context
             */
            @Override
            public void invoke(T data, AnalysisContext context) {
                List<R> ts = function.apply(data);
                dataList.addAll(ts);
                // 达到阈值就处理一次存储的数据
                if (dataList.size() >= threshold) {
                    consumer.accept(dataList);
                    dataList.clear();
                }
            }

            /**
             * excel文件解析完成后,事件调度中心会通知到该方法, 订阅者2
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 最后阈值外的数据做处理
                if (dataList.size() > 0) {
                    consumer.accept(dataList);
                }
            }
        };

    }

    /**
     * 描述: 获取读取Excel的监听器对象, 不指定阈值, 默认阈值为 2000
     *
     * @param consumer
     * @return {@link AnalysisEventListener<T>}
     * @author: HuangChao
     * @since: 2023/7/31 15:55
     */
    public static <T> AnalysisEventListener<T> getReadListener(Consumer<List<T>>
                                                                       consumer) {
        return getReadListener(consumer, GlobalConsts.NUM_2000);
    }

    /**
     * 描述: 获取读取Excel的监听器对象, 不指定阈值, 默认阈值为 2000
     *
     * @param function
     * @param consumer
     * @return {@link AnalysisEventListener<T>}
     * @author: HuangChao
     * @since: 2023/7/31 15:55
     */
    public static <T, R> AnalysisEventListener<T> getReadListener(Function<T, List<R>> function, Consumer<List<R>> consumer) {
        return getReadListener(function, consumer, GlobalConsts.NUM_2000);
    }
}