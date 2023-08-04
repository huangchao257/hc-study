package com.hc.virtualwallet.controller;

import com.hc.virtualwallet.service.VirtualWalletService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 类描述: 虚拟钱包表现层
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:34
 */
@RestController
public class VirtualWalletController {

    @Resource
    private VirtualWalletService mvcVirtualWalletService;
    @Resource
    private VirtualWalletService dddVirtualWalletService;

    //查询余额
    public BigDecimal getBalance(Long walletId) {
        return mvcVirtualWalletService.getBalance(walletId);
    }

    //出账
    public void debit(Long walletId, BigDecimal amount) {
        mvcVirtualWalletService.debit(walletId, amount);
    }


    //入账
    public void credit(Long walletId, BigDecimal amount) {
        mvcVirtualWalletService.credit(walletId, amount);
    }


    //转账
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        mvcVirtualWalletService.transfer(fromWalletId, toWalletId, amount);
    }
}
