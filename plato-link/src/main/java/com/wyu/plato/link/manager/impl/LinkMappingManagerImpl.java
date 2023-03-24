package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.link.manager.LinkManager;
import com.wyu.plato.link.manager.LinkMappingManager;
import com.wyu.plato.link.mapper.LinkMappingMapper;
import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.model.LinkMappingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-03-24
 */
@Component
public class LinkMappingManagerImpl implements LinkMappingManager {

    @Autowired
    private LinkMappingMapper linkMappingMapper;

    @Override
    public LinkMappingDO findOneByCode(String code) {
        return this.linkMappingMapper
                .selectOne(new QueryWrapper<LinkMappingDO>().lambda().eq(LinkMappingDO::getCode, code));
    }

    @Override
    public int save(LinkMappingDO linkDO) {
        return this.linkMappingMapper.insert(linkDO);
    }
}
