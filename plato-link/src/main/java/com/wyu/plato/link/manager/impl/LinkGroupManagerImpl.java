package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.link.manager.LinkGroupManager;
import com.wyu.plato.link.mapper.LinkGroupMapper;
import com.wyu.plato.link.model.LinkGroupDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-22
 */
@Component
public class LinkGroupManagerImpl implements LinkGroupManager {

    @Autowired
    private LinkGroupMapper linkGroupMapper;

    @Override
    public LinkGroupDO findById(Long id, Long accountNo) {
        return this.linkGroupMapper
                .selectOne(new QueryWrapper<LinkGroupDO>().lambda().eq(LinkGroupDO::getAccountNo, accountNo).eq(LinkGroupDO::getId, id));
    }

    @Override
    public int deleteGroup(Long id, Long accountNo) {
        return this.linkGroupMapper.deleteGroup(id, accountNo);
    }

    @Override
    public int insert(LinkGroupDO linkGroupDO) {
        return this.linkGroupMapper.insert(linkGroupDO);
    }

    @Override
    public List<LinkGroupDO> findAll(Long accountNo) {
        return this.linkGroupMapper
                .selectList(new QueryWrapper<LinkGroupDO>().lambda().eq(LinkGroupDO::getAccountNo, accountNo));
    }

    @Override
    public int update(LinkGroupDO groupDO, Long id, Long accountNo) {
        return this.linkGroupMapper
                .update(groupDO, new QueryWrapper<LinkGroupDO>().lambda().eq(LinkGroupDO::getAccountNo, accountNo).eq(LinkGroupDO::getId, id));
    }
}
