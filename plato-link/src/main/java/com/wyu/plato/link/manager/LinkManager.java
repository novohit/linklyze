package com.wyu.plato.link.manager;

import com.wyu.plato.link.model.LinkDO;

/**
 * @author novo
 * @since 2023-03-22
 */
public interface LinkManager {

    LinkDO findOneByCode(String code);

    int save(LinkDO linkDO);

    int update(LinkDO linkDO, Long accountNo);

    int delete(LinkDO linkDO, Long accountNo);
}
