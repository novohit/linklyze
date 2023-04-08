package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.link.manager.LinkMappingManager;
import com.wyu.plato.link.mapper.LinkMappingMapper;
import com.wyu.plato.link.model.LinkMappingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Map<Long, Map<String, Object>> groupLinkSum(Long accountNo, List<Long> groupIds) {
        Map<Long, Map<String, Object>> groupLinkSum = this.linkMappingMapper.groupLinkSum(accountNo, groupIds);
        return groupLinkSum;
    }

    /**
     * 分库accountNo 分表groupId
     *
     * @param mappingDO
     * @param accountNo
     * @return
     */
    @Override
    public int update(LinkMappingDO mappingDO, Long accountNo) {
        // TODO group_id暂时不允许更改 因为是表partition key 涉及到数据迁移
        return this.linkMappingMapper
                .update(mappingDO, new QueryWrapper<LinkMappingDO>().lambda()
                        .eq(LinkMappingDO::getAccountNo, accountNo)
                        .eq(LinkMappingDO::getGroupId, mappingDO.getGroupId())
                        .eq(LinkMappingDO::getId, mappingDO.getId()));
    }

    @Override
    public int delete(LinkMappingDO mappingDO, Long accountNo) {
        return this.linkMappingMapper
                .delete(new QueryWrapper<LinkMappingDO>().lambda()
                        .eq(LinkMappingDO::getAccountNo, accountNo)
                        .eq(LinkMappingDO::getGroupId, mappingDO.getGroupId())
                        .eq(LinkMappingDO::getId, mappingDO.getId()));
    }
}
