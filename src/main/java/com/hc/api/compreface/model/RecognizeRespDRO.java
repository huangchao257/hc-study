package com.hc.api.compreface.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/11/11
 */
@Data
public class RecognizeRespDRO implements Serializable {

    private List<RecognizeDRO> result;
}
