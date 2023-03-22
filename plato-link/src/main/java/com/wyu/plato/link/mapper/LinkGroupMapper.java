package com.wyu.plato.link.mapper;

import com.wyu.plato.link.model.LinkGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface LinkGroupMapper extends BaseMapper<LinkGroupDO> {

    int deleteGroup(@Param("id") Long id, @Param("accountNo") Long accountNo);
}
