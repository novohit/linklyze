package com.linklyze.visual.model;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class TypeGroupByDO {

    private String deviceType;

    private String os;

    private String browserType;

    private Long pv = 0L;

    private Long uv = 0L;
}
