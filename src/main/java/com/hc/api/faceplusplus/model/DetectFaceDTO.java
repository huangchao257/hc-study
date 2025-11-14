package com.hc.api.faceplusplus.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2025/02/26
 */
@Data
public class DetectFaceDTO implements Serializable {

    private String face_token;
    private String face_rectangle;
    private String landmark;
}
