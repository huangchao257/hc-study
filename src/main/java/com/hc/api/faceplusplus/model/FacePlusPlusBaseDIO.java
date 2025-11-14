package com.hc.api.faceplusplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 类描述: 旷视Face++请求基类
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
@Schema(description = "旷视Face++请求基类")
@Data
public class FacePlusPlusBaseDIO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1488708697827495645L;

    @Schema(description = "API Key")
    private String api_key;

    @Schema(description = "API Secret")
    private String api_secret;

}
