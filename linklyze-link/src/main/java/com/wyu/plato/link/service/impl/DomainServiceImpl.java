package com.wyu.plato.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.LocalUserThreadHolder;
import com.wyu.plato.link.manager.DomainManager;
import com.wyu.plato.link.mapper.DomainMapper;
import com.wyu.plato.link.model.DomainDO;
import com.wyu.plato.link.service.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-16
 */
@Service
@Slf4j
public class DomainServiceImpl extends ServiceImpl<DomainMapper, DomainDO> implements DomainService {

    @Autowired
    private DomainManager domainManager;

    @Override
    public List<DomainDO> findAll() {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        return this.domainManager.findAvailable(accountNo);
    }
}
