package com.wyu.plato.link.service;

import com.wyu.plato.common.model.CustomMessage;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.model.LinkDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface LinkService extends IService<LinkDO> {

    LinkDO findOneByCode(String code);

    void create(LinkCreateRequest linkCreateRequest);

    void handleCreate(CustomMessage customMessage);
}
