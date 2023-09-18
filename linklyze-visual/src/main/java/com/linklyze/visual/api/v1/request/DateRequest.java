package com.linklyze.visual.api.v1.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class DateRequest {

    /**
     * 短链码
     */
    @NotBlank
    private String code;

    /**
     * 开始区间(毫秒级)
     */
    @NotNull
    private Date start;

    /**
     * 结束区间(毫秒级)
     */
    @NotNull
    private Date end;

    /**
     * 非必传 默认值5 referer接口传 TopN
     */
    @Range(min = 1, max = 20)
    private Integer n = 5;
}
