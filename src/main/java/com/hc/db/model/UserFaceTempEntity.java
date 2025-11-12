package com.hc.db.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "user_face_temp", dataSource = "localWork")
public class UserFaceTempEntity {

    /**
     * 用户id
     */
    @Id
    private Long userId;

    /**
     * 用户名称
     */
    @Column(value = "user_name")
    private String userName;

    /**
     * 用户类型
     */
    @Column(value = "user_type")
    private Integer userType;

    /**
     * 人脸id
     */
    @Column(value = "face_id")
    private String faceId;

    /**
     * 人脸主题
     */
    @Column(value = "subject")
    private String subject;

    /**
     * 用户人脸信息表
     */
    @Column(value = "avatar_src")
    private String avatarSrc;


}
