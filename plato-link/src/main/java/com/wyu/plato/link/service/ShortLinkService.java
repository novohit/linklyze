package com.wyu.plato.link.service;

import com.wyu.plato.link.model.ShortLinkDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author novo
 * @since 2023-03-11
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    ShortLinkDO findOneByCode(String code);
}
