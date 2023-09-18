package com.linklyze.link.service;

import com.linklyze.link.model.LinkGroupDO;
import com.linklyze.link.api.v1.request.GroupCreateRequest;
import com.linklyze.link.api.v1.request.GroupUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface LinkGroupService extends IService<LinkGroupDO> {

    void create(GroupCreateRequest createRequest);

    void delete(Long groupId);

    LinkGroupDO findOne(Long groupId);

    List<LinkGroupDO> findAll();

    void update(GroupUpdateRequest updateRequest);
}
