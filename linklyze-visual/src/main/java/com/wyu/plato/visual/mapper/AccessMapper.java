package com.wyu.plato.visual.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.plato.visual.model.RefererGroupByDO;
import com.wyu.plato.visual.model.TypeGroupByDO;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.model.TrendGroupByDO;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccessMapper extends BaseMapper<DwsWideInfo> {

    List<RegionStatsVO> region(@Param("code") String code, @Param("start") String start, @Param("end") String end);

    List<TypeGroupByDO> type(@Param("code") String code, @Param("start") String start, @Param("end") String end);

    List<TrendGroupByDO> trendByDay(@Param("code") String code, @Param("start") String start, @Param("end") String end);

    List<TrendGroupByDO> trendByHour(@Param("code") String code, @Param("date") String date);

    List<RefererGroupByDO> refererTopN(@Param("code") String code, @Param("start") String start, @Param("end") String end, @Param("n") Integer n);

    List<DwsWideInfo> pageRecord(@Param("code") String code, @Param("offset") int offset, @Param("size") int size);

    int pagRecordTotal(@Param("code") String code, @Param("maxLimit") Integer maxLimit);
}
