package com.hc.api.compreface.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/11/11
 */
@Data
public class RecognizeDRO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2318487205247452019L;

    private Box box;

    private List<RecognizeSubjectDRO> subjects;

    @ToString
    @Data
    public static class Box implements Serializable{
        @Serial
        private static final long serialVersionUID = -2624373386316906633L;

        private BigDecimal probability;
        private Integer x_max;
        private Integer y_max;
        private Integer x_min;
        private Integer y_min;
    }

    @ToString
    @Data
    public static class RecognizeSubjectDRO implements Serializable{
        @Serial
        private static final long serialVersionUID = -8070399579088002794L;


        private String subject;
        private String similarity;
    }
}
