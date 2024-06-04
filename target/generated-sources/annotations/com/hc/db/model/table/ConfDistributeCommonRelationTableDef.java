package com.hc.db.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ConfDistributeCommonRelationTableDef extends TableDef {

    /**
     * 派单通用关联配置 实体类。

 @author mybatis-flex-helper automatic generation
 @since 1.0
     */
    public static final ConfDistributeCommonRelationTableDef CONF_DISTRIBUTE_COMMON_RELATION = new ConfDistributeCommonRelationTableDef();

    /**
     * 业务ID
     */
    public final QueryColumn BIZ_ID = new QueryColumn(this, "biz_id");

    /**
     * 数据key
     */
    public final QueryColumn MAP_KEY = new QueryColumn(this, "map_key");

    /**
     * 创建人
     */
    public final QueryColumn CREATER = new QueryColumn(this, "creater");

    /**
     * 更新人
     */
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");

    /**
     * 数据类型;参考：DistributeDataTypeEnum
     */
    public final QueryColumn DATA_TYPE = new QueryColumn(this, "data_type");

    /**
     * 数据值
     */
    public final QueryColumn MAP_VALUE = new QueryColumn(this, "map_value");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 派单关联ID
     */
    public final QueryColumn RELATION_ID = new QueryColumn(this, "relation_id");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{BIZ_ID, MAP_KEY, CREATER, UPDATER, DATA_TYPE, MAP_VALUE, CREATE_TIME, RELATION_ID, UPDATE_TIME};

    public ConfDistributeCommonRelationTableDef() {
        super("", "conf_distribute_common_relation");
    }

}
