package com.hc.virtualwallet.service;


import java.math.BigDecimal;

/**
 * 类描述: 虚拟钱包实现层
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:40
 */
public interface VirtualWalletService {

    BigDecimal getBalance(Long walletId);

    void debit(Long walletId, BigDecimal amount);

    void credit(Long walletId, BigDecimal amount);

    void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount);
}
