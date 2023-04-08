package com.wyu.plato.visual.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.visual.api.v1.request.DeviceRequest;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.RegionRequest;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.vo.StatsListVO;
import com.wyu.plato.visual.vo.RegionStatsVO;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-07
 */
public interface AccessService {
    Page<DwsWideInfo> page(PageRequest pageRequest);

    List<RegionStatsVO> region(RegionRequest regionRequest);

    StatsListVO device(DeviceRequest deviceRequest);
}
