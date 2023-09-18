package com.linklyze.link.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.linklyze.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("domain")
public class DomainDO extends BaseModel {


    /**
     * 用户自己绑定的域名
     */
    private Long accountNo;

    /**
     * 域名类型，自定义custom, 内置offical
     */
    private String domainType;

    private String value;


}
