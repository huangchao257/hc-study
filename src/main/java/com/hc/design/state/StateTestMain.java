package com.hc.design.state;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2023/08/07 18:41
 */
public class StateTestMain {

    public static void main(String[] args) {
        WorkStateMachine machine = new WorkStateMachine();
        machine.setState(new WorkNewState());
        machine.handle();
        machine.setState(new WorkDistributeState());
        machine.handle();


    }
}
