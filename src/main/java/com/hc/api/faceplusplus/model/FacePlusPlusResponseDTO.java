package com.hc.api.faceplusplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 类描述: 旷视 Face++响应基类
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
@Schema(description = "旷视 Face++响应基类")
@Data
public class FacePlusPlusResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6111920092880366583L;

    @Schema(description = "请求ID")
    public String request_id;

    @Schema(description = "请求耗时")
    public String time_used;

    @Schema(description = "错误信息")
    public String error_message;
}
