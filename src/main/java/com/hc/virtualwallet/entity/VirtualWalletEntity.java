package com.hc.virtualwallet.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2022/12/30 14:08
 */
@Data
public class VirtualWalletEntity {
    private Long id;
    private Long createTime;
    private BigDecimal balance;
}
