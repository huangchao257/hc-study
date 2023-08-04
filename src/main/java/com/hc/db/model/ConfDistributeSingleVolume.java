package com.hc.db.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 派单单量配置(ConfDistributeSingleVolume)实体类
 *
 * @author huangchao
 * @since 2023-07-04 18:35:16
 */
@Table(value = "conf_distribute_single_volume", dataSource = "localWork")
@Data
@Accessors(chain = true)
public class ConfDistributeSingleVolume implements Serializable {
    private static final long serialVersionUID = 914698858133684497L;
    @Id(keyType = KeyType.Auto)
    private Integer relationId;
    /**
     * 配置ID
     */
    private Integer confId;
    /**
     * 产品组ID
     */
    private Integer productGroupId;
    /**
     * 工程师等级
     */
    private Integer engineerLevel;
    /**
     * 谷值(周)
     */
    private BigDecimal valleyValue;
    /**
     * 合格值(周)
     */
    private BigDecimal passValue;
    /**
     * 峰值(周)
     */
    private BigDecimal peakValue;
    /**
     * 当前周;{1:{"valleyValue":1,"passValue":1,"peakValue":1}}
     */
    private String currentWeekJson;
    /**
     * 类型;10 当前 20下一周期
     */
    private Integer type;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updater;
    /**
     * 更新时间
     */
    private Date updateTime;
}

