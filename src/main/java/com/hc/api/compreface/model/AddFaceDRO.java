package com.hc.api.compreface.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/11/10
 */
@Data
public class AddFaceDRO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3974541011969992566L;

    private String image_id;
    private String subject;
}
