package com.hc.api.faceplusplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 类描述: 创建人脸库DIO
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
@Schema(description = "创建人脸库DIO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateFaceSetDIO extends FacePlusPlusBaseDIO {
    @Serial
    private static final long serialVersionUID = -1792768185526824211L;

    @Schema(description = "自定义标识")
    private String outer_id;

    @Schema(description = "人脸集合名称")
    private String display_name;

}
