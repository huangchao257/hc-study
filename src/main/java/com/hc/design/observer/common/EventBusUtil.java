package com.hc.design.observer.common;

import java.util.concurrent.*;

/**
 * 描述: 事件总线工具类-用于获取EventBus和发送事件
 *
 * @author: HuangChao
 * @since: 2023/7/18 14:46
 */
public class EventBusUtil {
 
    private static EventBus eventBus;
    private static AsyncEventBus asyncEventBus;
    private static Executor executor = new ThreadPoolExecutor(2, 10, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
 
        /**
         * 异步事件单例模式
         * @return
         */
    private static synchronized AsyncEventBus getAsyncEventBus() {
        if(asyncEventBus == null){
            asyncEventBus = new AsyncEventBus(executor);
        }
        return asyncEventBus;
    }
 
    /**
     * 同步事件单例模式
     * @return
     */
    private static synchronized EventBus getEventBus() {
        if(eventBus == null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }
 
    /**
     * 同步发送事件
     * @param event
     */
    public static void post(Object event) {
        getEventBus().post(event);
    }
 
    /**
     * 异步发送事件
     * @param event
     */
    public static void asyncPost(Object event) {
        getAsyncEventBus().post(event);
    }
 
    /**
     * 监听器注册
     * @param object
     */
    public static void register(Object object) {
        getEventBus().register(object);
        getAsyncEventBus().register(object);
    }
 
}
