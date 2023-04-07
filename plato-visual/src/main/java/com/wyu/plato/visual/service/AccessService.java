package com.wyu.plato.visual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.model.DwsWideInfo;

/**
 * @author novo
 * @since 2023-04-07
 */
public interface AccessService {
    Page<DwsWideInfo> page(PageRequest pageRequest);
}
