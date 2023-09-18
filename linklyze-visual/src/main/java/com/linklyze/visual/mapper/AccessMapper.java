package com.linklyze.visual.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linklyze.visual.vo.RegionStatsVO;
import com.linklyze.visual.model.RefererGroupByDO;
import com.linklyze.visual.model.TypeGroupByDO;
import com.linklyze.visual.model.DwsWideInfo;
import com.linklyze.visual.model.TrendGroupByDO;
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
