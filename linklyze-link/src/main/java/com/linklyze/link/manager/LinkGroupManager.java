package com.linklyze.link.manager;

import com.linklyze.link.model.LinkGroupDO;

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
