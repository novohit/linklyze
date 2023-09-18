package com.linklyze.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linklyze.link.manager.LinkMappingManager;
import com.linklyze.link.mapper.LinkGroupMapper;
import com.linklyze.link.model.LinkGroupDO;
import com.linklyze.link.manager.LinkGroupManager;
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
        // 查询用户所有分组内短链总数
        Map<Long, Map<String, Object>> map = this.linkMappingManager.groupLinkSum(accountNo, groupIds);
        List<LinkGroupDO> res = groupList.stream()
                .peek(groupDO -> {
                    // 获取一个分组内的短链总数 key为group_id value是一个HashMap  value:{"groupId":xxxxxxx,"link_sum":5}
                    // 分组查询如果count(*)==null 是没有数据的，如果没有分库分表，在查询语句再套一层自连接去重是可以实现null=0的
                    // 但是分库分表后语句可能不支持，这里直接判空
                    Map<String, Object> column = map.get(groupDO.getId());
                    if (column == null) {
                        groupDO.setLinkSum(0L);
                    } else {
                        Long linkSum = (Long) column.getOrDefault("link_sum", 0L);
                        groupDO.setLinkSum(linkSum);
                    }
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
