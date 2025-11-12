package com.hc.api.yinshi.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/11/11
 */
@Data
public class YsAlarmMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = 8449829946591978392L;
    private String devSerial;        // 设备序列号
    private Integer channel;         // 设备通道号
    private Integer channelType;     // 设备通道类型
    private String alarmType;        // 告警类型
    private String alarmId;          // 告警ID
    private String relationId;       // 告警关联ID
    private String location;         // 告警位置信息
    private String describe;         // 告警描述
    private String alarmTime;        // 告警时间
    private String customType;       // 自定义协议类型
    private Long requestTime;        // 服务端请求时间
    private String channelName;      // 告警通道名称
    private String checksum;         // 设备加密密码
    private Integer crypt;           // 图片加密类型
    private String customInfo;       // 报警自定义信息
    private String intelligentData;  // 智能识别信息
    private List<PictureInfo> pictureList; // 告警图片列表
    private String shortUrl;         // 告警图片短地址
    private String id;               // 平台告警Id
    private String url;              // 告警图片URL

    @ToString
    @Data
    public static class PictureInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = -7697881498383227897L;
        private String url;      // 图片URL
        private String shortUrl; // 图片短地址
    }
}
