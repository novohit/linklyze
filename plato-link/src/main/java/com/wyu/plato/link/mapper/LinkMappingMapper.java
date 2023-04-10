package com.wyu.plato.link.mapper;

import com.wyu.plato.link.model.LinkMappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author novo
 * @since 2023-03-16
 */
public interface LinkMappingMapper extends BaseMapper<LinkMappingDO> {

    @MapKey(value = "group_id")
    Map<Long, Map<String, Object>> groupLinkSum(@Param("accountNo") Long accountNo, @Param("groupIds") List<Long> groupIds);

}
