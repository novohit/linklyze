package com.wyu.plato.link.manager;

import com.wyu.plato.link.model.DomainDO;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-17
 */
public interface DomainManager {

    DomainDO findById(Long id, Long accountNo);

    List<DomainDO> findAvailable(Long accountNo);

    int create(DomainDO domainDO);

    /**
     * 查看官方内置的所有域名
     *
     * @return
     */
    List<DomainDO> findOfficialAll();

    /**
     * 查看商家的自定义域名
     *
     * @param accountNo
     * @return
     */
    List<DomainDO> findCustomAll(Long accountNo);

    int delete(Long id, Long accountNo);
}
