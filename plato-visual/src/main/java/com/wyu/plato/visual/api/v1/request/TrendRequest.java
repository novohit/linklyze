package com.wyu.plato.visual.api.v1.request;

import lombok.Data;

import java.util.Date;

/**
 * @author novo
 * @since 2023-04-09
 */
@Data
public class TrendRequest {

    /**
     * 开始区间
     */
    private Date start;

    /**
     * 结束区间
     */
    private Date end;
}
