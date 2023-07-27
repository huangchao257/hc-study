package com.hc.design.observer.common;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 类描述: 事件总线-同步阻塞模式
 *
 * @author: HuangChao
 * @since: 2023/7/18 10:57
 */
public class EventBus {
  /**
   * 线程池
   */
  private Executor executor;

  /**
   * 监听注册器
   */
  private ObserverRegistry registry = new ObserverRegistry();

  /**
   * 描述: 构造方法
   *
   * @author: HuangChao
   * @since: 2023/7/18 14:43
   */
  public EventBus() {
    // MoreExecutors.directExecutor()  Guava库中的一个方法，它返回一个特殊的执行器（Executor），该执行器会直接在当前线程中执行提交的任务，而不会创建新的线程
    // 此处使用他是为了统一格式都使用线程池去处构建事件主线
//    this(MoreExecutors.directExecutor());
    this(DirectExecutor.INSTANCE);
  }

  /**
   * 描述: 构造方法
   *
   * @param executor
   * @author: HuangChao
   * @since: 2023/7/18 14:42
   */
  protected EventBus(Executor executor) {
    this.executor = executor;
  }

  /**
   * 描述: 注册方法
   *
   * @param object
   * @author: HuangChao
   * @since: 2023/7/18 14:42
   */
  public void register(Object object) {
    registry.register(object);
  }

  /**
   * 描述: 向观察者发送消息
   *
   * @param event
   * @author: HuangChao
   * @since: 2023/7/18 10:58
   */
  public void post(Object event) {
    // 通过事件获取对应所有的观察者列表
    List<ObserverAction> observerActions = registry.getMatchedObserverActions(event);
    for (ObserverAction observerAction : observerActions) {
      // 通知观察者并执行方法
      executor.execute(new Runnable() {
        @Override
        public void run() {
          observerAction.execute(event);
        }
      });
    }
  }

  /**
   * 描述: 默认线程池-当前线程为执行线程
   *
   * @author: HuangChao
   * @since: 2023/7/18 14:43
   */
  private static enum DirectExecutor implements Executor {
    /**
     * 实例
     */
    INSTANCE;

    private DirectExecutor() {
    }

    public void execute(Runnable command) {
      command.run();
    }

    public String toString() {
      return "MoreExecutors.directExecutor()";
    }
  }
}