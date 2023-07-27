package com.hc.virtualwallet.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2022/12/30 14:12
 */
@Data
public class VirtualWalletTransactionEntity {
    private Long fromWalletId;
    private Long toWalletId;
    private Long createTime;
    private BigDecimal balance;
    private BigDecimal amount;
    private Integer type;
}
