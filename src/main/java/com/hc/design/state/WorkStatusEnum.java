package com.hc.design.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类描述: 工单状态枚举
 *
 * @author: HuangChao
 * @since: 2023/08/07 18:09
 */
@AllArgsConstructor
@Getter
public enum WorkStatusEnum {

    /**
     * 工单状态-新建
     */
    NEW(10, "新建"),

    DISTRIBUTE(20,"派单"),

    TAKE(30,"领单"),

    VISIT(40, "上门"),

    COMPLETE(50, "完成"),
    ;

    private Integer code;

    private String name;

}
