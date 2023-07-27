package com.hc.design.observer.common;

import java.util.concurrent.Executor;

/**
 * 描述: 事件总线-异步阻塞模式
 *
 * @author: HuangChao
 * @since: 2023/7/18 10:57
 */
public class AsyncEventBus extends EventBus {
  public AsyncEventBus(Executor executor) {
    super(executor);
  }
}