package com.linklyze.link.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.link.model.LinkDO;
import com.linklyze.link.model.LinkMappingDO;
import com.linklyze.link.api.v1.request.LinkCreateRequest;
import com.linklyze.link.api.v1.request.LinkDeleteRequest;
import com.linklyze.link.api.v1.request.LinkUpdateRequest;
import com.linklyze.link.api.v1.request.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface LinkService extends IService<LinkDO> {

    LinkDO findOneByCode(String code);

    void create(LinkCreateRequest linkCreateRequest);

    void handleCreate(CustomMessage customMessage);

    Page<LinkMappingDO> page(PageRequest pageRequest);

    void update(LinkUpdateRequest linkUpdateRequest);

    void handleUpdate(CustomMessage customMessage);

    void delete(LinkDeleteRequest linkDeleteRequest);

    void handleDelete(CustomMessage customMessage);
}
