package com.hc.design.observer.business;

import com.hc.design.observer.common.Subscribe;

/**
 * 类描述: 事件监听类
 *
 * @author: HuangChao
 * @since: 2023/07/27 14:08
 */
public class EventListener {
    @Subscribe
    public void testEventHandle1(TestEvent event) throws InterruptedException {
        System.out.println(String.format("testEventHandle1 线程[%s] 处理事件 id=%d ", Thread.currentThread().getName(), event.getId()));
//        Thread.sleep(1000L);
    }

    @Subscribe
    public void testEventHandle2(TestEvent event) {
        System.out.println(String.format("testEventHandle2 线程[%s] 处理事件 id=%d ", Thread.currentThread().getName(), event.getId()));
    }

}
