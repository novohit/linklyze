package com.wyu.plato.visual.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyu.plato.common.enums.TrendIntervalType;
import lombok.Data;

/**
 * @author novo
 * @since 2023-04-09
 */
@Data
public class TrendGroupByDO {
    private String interval;

    private Long pv = 0L;

    private Long uv = 0L;

    private Long ip = 0L;

    @TableField(exist = false)
    @JsonIgnore
    private TrendIntervalType type = TrendIntervalType.DAY;
}
