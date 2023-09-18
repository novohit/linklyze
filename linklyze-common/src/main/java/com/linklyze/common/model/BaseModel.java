package com.linklyze.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author novo
 * @since 2023-02-21 22:37
 */
@Data
public class BaseModel {

    /**
     * id
     */
    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableLogic(value = "null", delval = "now()")
    @JsonIgnore
    private Date deleteTime;
}
