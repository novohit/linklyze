package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.link.manager.LinkManager;
import com.wyu.plato.link.mapper.LinkMapper;
import com.wyu.plato.link.model.LinkDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-03-22
 */
@Component
public class LinkManagerImpl implements LinkManager {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public LinkDO findOneByCode(String code) {
        return this.linkMapper
                .selectOne(new QueryWrapper<LinkDO>().lambda().eq(LinkDO::getCode, code));
    }

    @Override
    public int save(LinkDO linkDO) {
        return this.linkMapper.insert(linkDO);
    }
}
