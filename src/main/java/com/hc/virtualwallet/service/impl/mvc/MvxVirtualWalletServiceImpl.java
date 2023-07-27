package com.hc.virtualwallet.service.impl.mvc;

import com.hc.virtualwallet.entity.VirtualWalletEntity;
import com.hc.virtualwallet.entity.VirtualWalletTransactionEntity;
import com.hc.virtualwallet.service.VirtualWalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:47
 */
@Service(value = "mvcVirtualWalletService")
public class MvxVirtualWalletServiceImpl implements VirtualWalletService {
    @Override
    public BigDecimal getBalance(Long walletId) {
        return BigDecimal.ZERO;
    }

    @Transactional
    @Override
    public void debit(Long walletId, BigDecimal amount) {
//        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWalletEntity walletEntity = new VirtualWalletEntity();
        BigDecimal balance = walletEntity.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("");
        }
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(Integer.MIN_VALUE);
        transactionEntity.setFromWalletId(walletId);
//        transactionRepo.saveTransaction(transactionEntity);
//        walletRepo.updateBalance(walletId, balance.subtract(amount));
    }

    @Transactional
    @Override
    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(Integer.MIN_VALUE);
        transactionEntity.setFromWalletId(walletId);
        // 保存
//        transactionRepo.saveTransaction(transactionEntity);
//        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
//        BigDecimal balance = walletEntity.getBalance();
//        walletRepo.updateBalance(walletId, balance.add(amount));
    }

    @Transactional
    @Override
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(Integer.MIN_VALUE);
        transactionEntity.setFromWalletId(fromWalletId);
        transactionEntity.setToWalletId(toWalletId);
        // 数据库保存
//        transactionRepo.saveTransaction(transactionEntity);
        debit(fromWalletId, amount);
        credit(toWalletId, amount);
    }

}
