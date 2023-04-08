package com.wyu.plato.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author novo
 * @since 2023-02-21 22:37
 */
@Data
public class BaseModel {

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@JsonIgnore
    private Date createTime;

    //@JsonIgnore
    private Date updateTime;

    @TableLogic(value = "null", delval = "now()")
    @JsonIgnore
    private Date deleteTime;
}
