package com.wyu.plato.link.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.link.model.LinkMappingDO;

import java.util.List;
import java.util.Map;

/**
 * @author novo
 * @since 2023-03-24
 */
public interface LinkMappingManager {
    LinkMappingDO findOneByCode(String code);

    int save(LinkMappingDO mappingDO);

    Page<LinkMappingDO> page(Long accountNo, Long groupId, Integer page, Integer size);

    Map<Long, Map<String, Object>> groupLinkSum(Long accountNo, List<Long> groupIds);

    int update(LinkMappingDO mappingDO, Long accountNo);

    int delete(LinkMappingDO mappingDO, Long accountNo);
}
