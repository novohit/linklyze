package com.wyu.plato.link.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wyu.plato.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("link_group")
public class LinkGroupDO extends BaseModel {


    /**
     * 短链分组名
     */
    private String title;

    /**
     * 账户唯一标识
     */
    private Long accountNo;


}
