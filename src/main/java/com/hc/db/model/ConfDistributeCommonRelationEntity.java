package com.hc.db.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 派单通用关联配置 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "conf_distribute_common_relation", dataSource = "localWork")
public class ConfDistributeCommonRelationEntity {

    /**
     * 派单关联ID
     */
    @Id(keyType = KeyType.Auto)
    private Integer relationId;

    /**
     * 业务ID
     */
    @Column(value = "biz_id")
    private Integer bizId;

    /**
     * 数据key
     */
    @Column(value = "map_key")
    private String mapKey;

    /**
     * 数据值
     */
    @Column(value = "map_value")
    private String mapValue;

    /**
     * 数据类型;参考：DistributeDataTypeEnum
     */
    @Column(value = "data_type")
    private Integer dataType;

    /**
     * 创建人
     */
    @Column(value = "creater")
    private String creater;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    private java.util.Date createTime;

    /**
     * 更新人
     */
    @Column(value = "updater")
    private String updater;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    private java.util.Date updateTime;


    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getMapValue() {
        return mapValue;
    }

    public void setMapValue(String mapValue) {
        this.mapValue = mapValue;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}
