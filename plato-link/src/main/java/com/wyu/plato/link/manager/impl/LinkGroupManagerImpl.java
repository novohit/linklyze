package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.link.manager.LinkGroupManager;
import com.wyu.plato.link.manager.LinkMappingManager;
import com.wyu.plato.link.mapper.LinkGroupMapper;
import com.wyu.plato.link.model.LinkGroupDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-03-22
 */
@Component
public class LinkGroupManagerImpl implements LinkGroupManager {

    @Autowired
    private LinkGroupMapper linkGroupMapper;

    @Autowired
    private LinkMappingManager linkMappingManager;

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
        List<LinkGroupDO> groupList = this.linkGroupMapper
                .selectList(new QueryWrapper<LinkGroupDO>().lambda().eq(LinkGroupDO::getAccountNo, accountNo));
        List<Long> groupIds = groupList.stream().map(LinkGroupDO::getId).collect(Collectors.toList());
        // 查询分组内短链总数
        Map<Long, Map<String, Object>> map = this.linkMappingManager.groupLinkSum(accountNo, groupIds);
        List<LinkGroupDO> res = groupList.stream()
                .peek(groupDO -> {
                    Map<String, Object> column = map.get(groupDO.getId());
                    Long linkSum = (Long) column.getOrDefault("link_sum", 0L);
                    groupDO.setLinkSum(linkSum);

                })
                .collect(Collectors.toList());
        return res;
    }

    @Override
    public int update(LinkGroupDO groupDO, Long accountNo) {
        return this.linkGroupMapper
                .update(groupDO, new QueryWrapper<LinkGroupDO>().lambda().eq(LinkGroupDO::getAccountNo, accountNo).eq(LinkGroupDO::getId, groupDO.getId()));
    }
}
