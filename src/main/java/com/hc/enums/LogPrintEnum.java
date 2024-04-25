package com.hc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类描述: 日志输出枚举
 *
 * @author: HuangChao
 * @since: 2023/09/07 10:36
 */
@AllArgsConstructor
@Getter
public enum LogPrintEnum {

    /**
     * 只是输出入参
     */
    PRINT_IN(true, false),
    /**
     * 只输出出参
     */
    PRINT_OUT(false, true),
    /**
     * 出入参全部输出
     */
    PRINT_ALL(true, true),
    /**
     * 出入参都不输出
     */
    DISABLE_PRINT(false, false),
    ;

    /**
     * 是否输出入参
     */
    private final boolean printIn;
    /**
     * 是否输出出参
     */
    private final boolean printOut;

}
