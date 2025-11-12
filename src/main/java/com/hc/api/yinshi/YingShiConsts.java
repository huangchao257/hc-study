package com.hc.api.yinshi;

import java.util.List;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/11/11
 */
public abstract class YingShiConsts {

    /**
     * 萤石接口域名
     */
    public static final String DOMAIN_NAME = "https://open.ys7.com/api/";

    /**
     * 设备布撤防
     */
    public static final String DEFENCE_SET = DOMAIN_NAME + "lapp/device/defence/set";
    /**
     * 萤石消息类型-报警消息
     */
    public static final String MSG_TYPE_ALARM = "ys.alarm";
    /**
     * 人脸检测事件
     */
    public static final String ALARM_TYPE_FACEDETECTION = "facedetection";
    /**
     * 人脸比对告警
     */
    public static final String ALARM_TYPE_FACE_COMPARE = "face_compare";
    /**
     * 智能人脸检测
     */
    public static final String ALARM_TYPE_SMART_FACE_DET = "SmartFaceDet";
    /**
     * 人脸抓图
     */
    public static final String ALARM_TYPE_FACE_CAPTURE = "faceCapture";
    /**
     * 检测到人脸
     */
    public static final String ALARM_TYPE_FACE_DETECT = "face_detect";
    /**
     * 人脸识别事件
     */
    public static final String ALARM_TYPE_VAS_FACE = "vas_face";

    /**
     * 告警类型列表
     */
    public static final List<String> ALARM_TYPE_LIST = List.of(ALARM_TYPE_FACEDETECTION, ALARM_TYPE_FACE_COMPARE, ALARM_TYPE_SMART_FACE_DET,
            ALARM_TYPE_FACE_CAPTURE, ALARM_TYPE_FACE_DETECT, ALARM_TYPE_VAS_FACE);


}
