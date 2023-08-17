package com.hc.design.state;

/**
 * 类描述: 状态机通用接口
 *
 * @author: HuangChao
 * @since: 2023/08/07 18:19
 */
public interface StateMachine {

    /**
     * 描述: 处理
     *
     * @return {@link Boolean}
     * @author: HuangChao
     * @since: 2023/8/7 18:38
     */
    Boolean handle();
}
