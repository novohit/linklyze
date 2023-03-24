package com.wyu.plato.link.manager;

import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.model.LinkMappingDO;

/**
 * @author novo
 * @since 2023-03-24
 */
public interface LinkMappingManager {
    LinkMappingDO findOneByCode(String code);

    int save(LinkMappingDO mappingDO);
}
