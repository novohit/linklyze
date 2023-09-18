package com.wyu.plato.link.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.common.model.bo.CustomMessage;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.api.v1.request.LinkDeleteRequest;
import com.wyu.plato.link.api.v1.request.LinkUpdateRequest;
import com.wyu.plato.link.api.v1.request.PageRequest;
import com.wyu.plato.link.model.LinkDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyu.plato.link.model.LinkMappingDO;

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
