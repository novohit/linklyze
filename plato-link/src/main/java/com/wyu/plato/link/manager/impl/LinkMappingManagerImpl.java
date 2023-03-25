package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public Page<LinkMappingDO> page(Long accountNo, Long groupId, Integer page, Integer size) {
        Page<LinkMappingDO> pageRequest = new Page<>(page, size);
        Page<LinkMappingDO> pageResp = this.linkMappingMapper
                .selectPage(pageRequest,
                        new QueryWrapper<LinkMappingDO>().lambda()
                                .eq(LinkMappingDO::getAccountNo, accountNo)
                                .eq(LinkMappingDO::getGroupId, groupId));
        return pageResp;
    }
}
