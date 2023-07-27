package com.hc.virtualwallet.model.domain;

import java.math.BigDecimal;

/**
 * 类描述: 充血模型-domain对象-本身封装逻辑，对外谨慎的暴露直接修改数据的方法
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:51
 */
public class VirtualWallet {
    private Long id;
    private Long createTime = System.currentTimeMillis();
    private BigDecimal balance = BigDecimal.ZERO;

    public VirtualWallet(Long walletId) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new RuntimeException();
        } this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException();
        } this.balance = this.balance.add(amount);
    }
}
