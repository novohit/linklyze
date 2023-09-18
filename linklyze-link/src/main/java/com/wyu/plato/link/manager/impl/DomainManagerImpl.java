package com.wyu.plato.link.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.common.enums.DomainType;
import com.wyu.plato.link.manager.DomainManager;
import com.wyu.plato.link.mapper.DomainMapper;
import com.wyu.plato.link.model.DomainDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-17
 */
@Component
public class DomainManagerImpl implements DomainManager {

    @Autowired
    private DomainMapper domainMapper;

    @Override
    public DomainDO findById(Long id, Long accountNo) {
        return this.domainMapper
                .selectOne(new QueryWrapper<DomainDO>().lambda().eq(DomainDO::getAccountNo, accountNo).eq(DomainDO::getId, id));
    }

    @Override
    public List<DomainDO> findAvailable(Long accountNo) {
        List<DomainDO> customAll = this.findCustomAll(accountNo);
        List<DomainDO> officialAll = this.findOfficialAll();
        customAll.addAll(officialAll);
        return customAll;
    }

    @Override
    public int create(DomainDO domainDO) {
        return this.domainMapper.insert(domainDO);
    }

    @Override
    public List<DomainDO> findOfficialAll() {
        return this.domainMapper
                .selectList(new QueryWrapper<DomainDO>().lambda().eq(DomainDO::getDomainType, DomainType.OFFICIAL.name()));
    }

    @Override
    public List<DomainDO> findCustomAll(Long accountNo) {
        return this.domainMapper
                .selectList(new QueryWrapper<DomainDO>().lambda().eq(DomainDO::getDomainType, DomainType.OFFICIAL.name()).eq(DomainDO::getAccountNo, accountNo));
    }

    @Override
    public int delete(Long id, Long accountNo) {
        return this.domainMapper
                .delete(new QueryWrapper<DomainDO>().lambda().eq(DomainDO::getAccountNo, accountNo).eq(DomainDO::getId, id));
    }
}
