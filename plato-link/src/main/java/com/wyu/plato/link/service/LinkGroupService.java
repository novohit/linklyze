package com.wyu.plato.link.service;

import com.wyu.plato.link.api.v1.request.LinkGroupCreateRequest;
import com.wyu.plato.link.api.v1.request.LinkGroupUpdateRequest;
import com.wyu.plato.link.model.LinkGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface LinkGroupService extends IService<LinkGroupDO> {

    void create(LinkGroupCreateRequest createRequest);

    void delete(Long groupId);

    LinkGroupDO findOne(Long groupId);

    List<LinkGroupDO> findAll();

    void update(LinkGroupUpdateRequest updateRequest);
}
