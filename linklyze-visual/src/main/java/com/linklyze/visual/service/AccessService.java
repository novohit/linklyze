package com.linklyze.visual.service;

import com.linklyze.common.model.vo.PageVO;
import com.linklyze.visual.api.v1.request.DateRequest;
import com.linklyze.visual.api.v1.request.PageRequest;
import com.linklyze.visual.model.DwsWideInfo;
import com.linklyze.visual.model.RefererGroupByDO;
import com.linklyze.visual.model.TrendGroupByDO;
import com.linklyze.visual.vo.RegionStatsVO;
import com.linklyze.visual.vo.StatsListVO;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-07
 */
public interface AccessService {
    PageVO<DwsWideInfo> page(PageRequest pageRequest);

    List<RegionStatsVO> region(DateRequest dateRequest);

    StatsListVO type(DateRequest dateRequest);

    List<TrendGroupByDO> trend(DateRequest dateRequest);

    List<RefererGroupByDO> refererTopN(DateRequest dateRequest);
}
