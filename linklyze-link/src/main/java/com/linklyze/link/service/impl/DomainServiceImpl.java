package com.linklyze.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.LocalUserThreadHolder;
import com.linklyze.link.mapper.DomainMapper;
import com.linklyze.link.manager.DomainManager;
import com.linklyze.link.model.DomainDO;
import com.linklyze.link.service.DomainService;
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
