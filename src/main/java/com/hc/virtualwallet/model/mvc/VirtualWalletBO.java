package com.hc.virtualwallet.model.mvc;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类描述: 贫血模型-BO对象-基本不包含业务逻辑
 *
 * @author: HuangChao
 * @since: 2022/12/30 11:52
 */
@Data
public class VirtualWalletBO {
    private Long id;
    private Long createTime;
    private BigDecimal balance;
}
