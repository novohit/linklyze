package com.linklyze.visual.model;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-09
 */
@Data
public class RefererGroupByDO {

    private String referer;

    private Long pv = 0L;
}
