package com.linklyze.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linklyze.link.model.DomainDO;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-16
 */
public interface DomainService extends IService<DomainDO> {

    List<DomainDO> findAll();
}
