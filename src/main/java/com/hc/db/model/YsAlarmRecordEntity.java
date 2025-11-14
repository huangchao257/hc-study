package com.hc.db.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * (YsAlarmRecord)实体类
 *
 * @author admin
 * @since 2025-11-13 14:10:58
 */

@Data
@Table(value = "ys_alarm_record", dataSource = "localWork")
public class YsAlarmRecordEntity implements Serializable {
    private static final long serialVersionUID = 551566360995761314L;
    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 报警时间
     */
    @Column(value = "alarm_time")
    private Date alarmTime;

    /**
     * 报警类型
     */
    @Column(value = "alarm_type")
    private String alarmType;

    /**
     * 设备序列号
     */
    @Column(value = "dev_serial")
    private String devSerial;

    /**
     * 图片路径
     */
    @Column(value = "img_path")
    private String imgPath;

    /**
     * 图片url
     */
    private String imgUrl;


}

