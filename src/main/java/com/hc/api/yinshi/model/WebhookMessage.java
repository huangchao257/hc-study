package com.hc.api.yinshi.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * 类描述: 萤石消息
 *
 * @author: HuangChao
 * @since: 2025/3/3
 */
@Data
@ToString
public class WebhookMessage implements Serializable {

    /**
     * 消息头
     */
    private WebhookMessageHeader header;

    /**
     * 消息体
     */
    private String body;

    @ToString
    @Data
    public class WebhookMessageHeader {

        /**
         * 消息id
         */
        private String messageId;

        /**
         * 设备序列号
         */
        private String deviceId;

        /**
         * 消息类型，需向消息管道服务申请
         */
        private String type;

        /**
         * 通道号
         */
        private Integer channelNo;

        /**
         * 消息推送时间
         */
        private Long messageTime;
    }
}

