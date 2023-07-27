package com.hc.virtualwallet.service.impl.ddd;

import com.hc.virtualwallet.entity.VirtualWalletEntity;
import com.hc.virtualwallet.entity.VirtualWalletTransactionEntity;
import com.hc.virtualwallet.model.domain.VirtualWallet;
import com.hc.virtualwallet.service.VirtualWalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:40
 */
@Service(value = "dddVirtualWalletService")
public class DddVirtualWalletServiceImpl implements VirtualWalletService {
    @Override
    public BigDecimal getBalance(Long walletId) {
        return null;
    }

    @Transactional
    @Override
    public void debit(Long walletId, BigDecimal amount) {
//        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWalletEntity walletEntity = new VirtualWalletEntity();
        VirtualWallet wallet = convert(walletEntity);
        wallet.debit(amount);
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(Integer.MIN_VALUE);
        transactionEntity.setFromWalletId(walletId);
//            transactionRepo.saveTransaction(transactionEntity);
//            walletRepo.updateBalance(walletId, wallet.balance());
    }

    @Transactional
    @Override
    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = new VirtualWalletEntity();
        VirtualWallet wallet = convert(walletEntity);
        wallet.credit(amount);
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(Integer.MIN_VALUE);
        transactionEntity.setFromWalletId(walletId);
//            transactionRepo.saveTransaction(transactionEntity);
//            walletRepo.updateBalance(walletId, wallet.balance());
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


    private VirtualWallet convert(VirtualWalletEntity walletEntity) {
        return new VirtualWallet(walletEntity.getId());
    }
}
