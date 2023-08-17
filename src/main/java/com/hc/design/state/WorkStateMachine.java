package com.hc.design.state;

import lombok.Data;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2023/08/07 18:19
 */
@Data
public class WorkStateMachine implements StateMachine{

    private WorkState state;

    @Override
    public Boolean handle() {
        return state.handle(this);
    }

}
