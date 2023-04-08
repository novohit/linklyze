package com.wyu.plato.visual.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccessMapper extends BaseMapper<DwsWideInfo> {

    List<RegionStatsVO> region(@Param("code") String code, @Param("start") String start, @Param("end") String end);
}
