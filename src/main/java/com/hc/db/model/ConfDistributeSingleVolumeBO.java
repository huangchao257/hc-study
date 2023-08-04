package com.hc.db.model;

import lombok.Data;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2023/07/05 17:55
 */
@Data
public class ConfDistributeSingleVolumeBO {

    /**
     * 谷值
     */
    private Integer valleyValue;
    /**
     * 合格值
     */
    private Integer passValue;
    /**
     * 峰值
     */
    private Integer peakValue;
}
