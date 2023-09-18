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

    /**
     * C端 code是partition key 所以要传code
     *
     * @param linkDO
     * @param accountNo
     * @return
     */
    @Override
    public int update(LinkDO linkDO, Long accountNo) {
        return this.linkMapper
                .update(linkDO, new QueryWrapper<LinkDO>().lambda()
                        .eq(LinkDO::getCode, linkDO.getCode())
                        .eq(LinkDO::getAccountNo, accountNo));
    }

    @Override
    public int delete(LinkDO linkDO, Long accountNo) {
        return this.linkMapper
                .delete(new QueryWrapper<LinkDO>().lambda()
                        .eq(LinkDO::getCode, linkDO.getCode())
                        .eq(LinkDO::getAccountNo, accountNo));
    }
}
