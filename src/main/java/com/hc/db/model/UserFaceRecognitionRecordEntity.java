package com.hc.db.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * (UserFaceRecognitionRecord)实体类
 *
 * @author admin
 * @since 2025-11-11 14:06:36
 */

@Data
@Table(value = "user_face_recognition_record", dataSource = "localWork")
public class UserFaceRecognitionRecordEntity implements Serializable {
    private static final long serialVersionUID = -22751374152927137L;
    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 图片url
     */
    @Column(value = "img_url")
    private String imgUrl;

    /**
     * 设备序列号
     */
    @Column(value = "dev_serial")
    private String devSerial;

    /**
     * 通道名称
     */
    @Column(value = "channel_name")
    private String channelName;

    /**
     * 报警时间
     */
    @Column(value = "alarm_time")
    private Date alarmTime;

    /**
     * 相似度
     */
    @Column(value = "probability")
    private BigDecimal probability;

    /**
     * 人脸主体
     */
    @Column(value = "subject")
    private String subject;

}

