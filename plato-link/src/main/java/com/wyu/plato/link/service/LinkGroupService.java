package com.wyu.plato.link.service;

import com.wyu.plato.link.api.v1.request.GroupCreateRequest;
import com.wyu.plato.link.api.v1.request.GroupUpdateRequest;
import com.wyu.plato.link.model.LinkGroupDO;
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
