package com.wyu.plato.visual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.common.util.TimeUtil;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.RegionRequest;
import com.wyu.plato.visual.mapper.AccessMapper;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.service.AccessService;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-07
 */
@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessMapper accessMapper;


    /**
     * TODO 避免深度分页
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Page<DwsWideInfo> page(PageRequest pageRequest) {
        Integer page = pageRequest.getPage();
        Integer size = pageRequest.getSize();
        Page<DwsWideInfo> request = new Page<>(page, size);
        Page<DwsWideInfo> pageResp = this.accessMapper.selectPage(request, new QueryWrapper<DwsWideInfo>()
                .lambda().eq(DwsWideInfo::getCode, pageRequest.getCode()));
        return pageResp;
    }

    @Override
    public List<RegionStatsVO> region(RegionRequest regionRequest) {
        // TODO 查询区间不能过大
        String start = TimeUtil.format(regionRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(regionRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        return this.accessMapper.region(regionRequest.getCode(), start, end);
    }
}
