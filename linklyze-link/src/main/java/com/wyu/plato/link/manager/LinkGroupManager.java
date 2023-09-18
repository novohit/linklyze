package com.wyu.plato.link.manager;

import com.wyu.plato.link.model.DomainDO;
import com.wyu.plato.link.model.LinkGroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-22
 */
public interface LinkGroupManager {
    LinkGroupDO findById(Long id, Long accountNo);

    int deleteGroup(Long id, Long accountNo);

    int insert(LinkGroupDO linkGroupDO);

    List<LinkGroupDO> findAll(Long accountNo);

    int update(LinkGroupDO groupDO, Long accountNo);
}
