package com.hc.design.observer.business;

import com.hc.design.observer.common.EventBusUtil;

/**
 * 类描述: 测试主类
 *
 * @author: HuangChao
 * @since: 2023/07/27 14:17
 */
public class TestMain {

    public static void main(String[] args) {
        TestEvent testEvent = new TestEvent();
        testEvent.setId(1);
        EventListener listener = new EventListener();
        EventBusUtil.register(listener);
        EventBusUtil.post(testEvent);
        EventBusUtil.asyncPost(testEvent);
        System.out.println(String.format("main 线程[%s] 处理事件 id=%d ", Thread.currentThread().getName(), testEvent.getId()));

    }
}
