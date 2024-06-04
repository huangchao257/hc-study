package com.hc.db.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ConfDistributeSingleVolumeTableDef extends TableDef {

    /**
     * 派单单量配置(ConfDistributeSingleVolume)实体类

 @author huangchao
 @since 2023-07-04 18:35:16
     */
    public static final ConfDistributeSingleVolumeTableDef CONF_DISTRIBUTE_SINGLE_VOLUME = new ConfDistributeSingleVolumeTableDef();

    /**
     * 类型;10 当前 20下一周期
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 配置ID
     */
    public final QueryColumn CONF_ID = new QueryColumn(this, "conf_id");

    /**
     * 创建人
     */
    public final QueryColumn CREATER = new QueryColumn(this, "creater");

    /**
     * 更新人
     */
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");

    /**
     * 合格值(周)
     */
    public final QueryColumn PASS_VALUE = new QueryColumn(this, "pass_value");

    /**
     * 峰值(周)
     */
    public final QueryColumn PEAK_VALUE = new QueryColumn(this, "peak_value");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn RELATION_ID = new QueryColumn(this, "relation_id");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 谷值(周)
     */
    public final QueryColumn VALLEY_VALUE = new QueryColumn(this, "valley_value");

    /**
     * 工程师等级
     */
    public final QueryColumn ENGINEER_LEVEL = new QueryColumn(this, "engineer_level");

    /**
     * 产品组ID
     */
    public final QueryColumn PRODUCT_GROUP_ID = new QueryColumn(this, "product_group_id");

    /**
     * 当前周;{1:{"valleyValue":1,"passValue":1,"peakValue":1}}
     */
    public final QueryColumn CURRENT_WEEK_JSON = new QueryColumn(this, "current_week_json");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{TYPE, CONF_ID, CREATER, UPDATER, PASS_VALUE, PEAK_VALUE, CREATE_TIME, RELATION_ID, UPDATE_TIME, VALLEY_VALUE, ENGINEER_LEVEL, PRODUCT_GROUP_ID, CURRENT_WEEK_JSON};

    public ConfDistributeSingleVolumeTableDef() {
        super("", "conf_distribute_single_volume");
    }

}
