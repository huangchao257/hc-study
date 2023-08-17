package com.hc.design.state;

/**
 * 类描述: 工单抽象状态
 *
 * @author: HuangChao
 * @since: 2023/08/07 18:08
 */
public interface WorkState {

    /**
     * 描述: 状态枚举
     *
     * @return {@link WorkStatusEnum}
     * @author: HuangChao
     * @since: 2023/8/7 18:34
     */
    WorkStatusEnum getState();

    /**
     * 描述: 工单状态处理
     *  负责变更成对应状态的后续处理和前置校验
     *
     * @param workStateMachine
     * @return {@link Boolean}
     * @author: HuangChao
     * @since: 2023/8/7 18:38
     */
    Boolean handle(WorkStateMachine workStateMachine);
}
