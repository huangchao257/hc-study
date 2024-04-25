package com.hc.db.model;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述: 派单方案配置
 *
 * @author: HuangChao
 * @since: 2023/02/09 13:53
 */
@Table(value = "conf_distribute_plan", dataSource = "zmnProd")
@Data
@Accessors(chain = true)
public class ConfDistributePlan implements Serializable {
    private static final long serialVersionUID = 6726537891496551501L;
    /**
     * 配置ID
     */
    private Integer confId;
    /**
     * 省份ID
     */
    private Integer provinceId;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市ID
     */
    private Integer cityId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 子公司ID
     */
    private Integer subCompanyId;
    /**
     * 子公司名称
     */
    private String subCompanyName;
    /**
     * 服务平台;10 啄木鸟，20 言而有信，30 川南环保
     */
    private Integer plat;
    /**
     * 白天派单方案状态;1.停用 2.启用
     */
    private Integer dayPlanStatus;
    /**
     * 白天派单方案;派单策略合集 以逗号分隔
     */
    private String dayPlan;
    /**
     * 夜间派单方案状态;1.停用 2.启用
     */
    private Integer nightPlanStatus;
    /**
     * 夜间派单方案;派单策略合集 以逗号分隔
     */
    private String nightPlan;
    /**
     * 返修派单方案状态;1.停用 2.启用
     */
    private Integer reworkPlanStatus;
    /**
     * 返修派单方案;派单策略合集 以逗号分隔
     */
    private String reworkPlan;
    /**
     * 自动派单时间
     */
    private String autoDistributeTimeStr;
    /**
     * 自动派单时间json;{1010:"03:03,07:07"}
     */
    private String autoDistributeTimeJson;

    /**
     * 人单值标准状态，1停用，2启用
     */
    private Integer singleStandardStatus;

    /**
     * 状态;1.停用 2.启用
     */
    private Integer status;
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
