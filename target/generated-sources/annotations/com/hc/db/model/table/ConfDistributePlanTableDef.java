package com.hc.db.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ConfDistributePlanTableDef extends TableDef {

    /**
     * 类描述: 派单方案配置

 @author: HuangChao
 @since: 2023/02/09 13:53
     */
    public static final ConfDistributePlanTableDef CONF_DISTRIBUTE_PLAN = new ConfDistributePlanTableDef();

    /**
     * 服务平台;10 啄木鸟，20 言而有信，30 川南环保
     */
    public final QueryColumn PLAT = new QueryColumn(this, "plat");

    /**
     * 城市ID
     */
    public final QueryColumn CITY_ID = new QueryColumn(this, "city_id");

    /**
     * 配置ID
     */
    public final QueryColumn CONF_ID = new QueryColumn(this, "conf_id");

    /**
     * 状态;1.停用 2.启用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 创建人
     */
    public final QueryColumn CREATER = new QueryColumn(this, "creater");

    /**
     * 白天派单方案;派单策略合集 以逗号分隔
     */
    public final QueryColumn DAY_PLAN = new QueryColumn(this, "day_plan");

    /**
     * 更新人
     */
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");

    /**
     * 城市名称
     */
    public final QueryColumn CITY_NAME = new QueryColumn(this, "city_name");

    /**
     * 夜间派单方案;派单策略合集 以逗号分隔
     */
    public final QueryColumn NIGHT_PLAN = new QueryColumn(this, "night_plan");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 省份ID
     */
    public final QueryColumn PROVINCE_ID = new QueryColumn(this, "province_id");

    /**
     * 返修派单方案;派单策略合集 以逗号分隔
     */
    public final QueryColumn REWORK_PLAN = new QueryColumn(this, "rework_plan");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 省份名称
     */
    public final QueryColumn PROVINCE_NAME = new QueryColumn(this, "province_name");

    /**
     * 子公司ID
     */
    public final QueryColumn SUB_COMPANY_ID = new QueryColumn(this, "sub_company_id");

    /**
     * 白天派单方案状态;1.停用 2.启用
     */
    public final QueryColumn DAY_PLAN_STATUS = new QueryColumn(this, "day_plan_status");

    /**
     * 子公司名称
     */
    public final QueryColumn SUB_COMPANY_NAME = new QueryColumn(this, "sub_company_name");

    /**
     * 夜间派单方案状态;1.停用 2.启用
     */
    public final QueryColumn NIGHT_PLAN_STATUS = new QueryColumn(this, "night_plan_status");

    /**
     * 返修派单方案状态;1.停用 2.启用
     */
    public final QueryColumn REWORK_PLAN_STATUS = new QueryColumn(this, "rework_plan_status");

    /**
     * 人单值标准状态，1停用，2启用
     */
    public final QueryColumn SINGLE_STANDARD_STATUS = new QueryColumn(this, "single_standard_status");

    /**
     * 自动派单时间
     */
    public final QueryColumn AUTO_DISTRIBUTE_TIME_STR = new QueryColumn(this, "auto_distribute_time_str");

    /**
     * 自动派单时间json;{1010:"03:03,07:07"}
     */
    public final QueryColumn AUTO_DISTRIBUTE_TIME_JSON = new QueryColumn(this, "auto_distribute_time_json");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{PLAT, CITY_ID, CONF_ID, STATUS, CREATER, DAY_PLAN, UPDATER, CITY_NAME, NIGHT_PLAN, CREATE_TIME, PROVINCE_ID, REWORK_PLAN, UPDATE_TIME, PROVINCE_NAME, SUB_COMPANY_ID, DAY_PLAN_STATUS, SUB_COMPANY_NAME, NIGHT_PLAN_STATUS, REWORK_PLAN_STATUS, SINGLE_STANDARD_STATUS, AUTO_DISTRIBUTE_TIME_STR, AUTO_DISTRIBUTE_TIME_JSON};

    public ConfDistributePlanTableDef() {
        super("", "conf_distribute_plan");
    }

}
