package com.linklyze.visual.api.v1;

import com.linklyze.common.constant.Constants;
import com.linklyze.common.enums.TrendIntervalType;
import com.linklyze.common.model.vo.PageResponse;
import com.linklyze.common.model.vo.Response;
import com.linklyze.common.util.TimeUtil;
import com.linklyze.visual.api.v1.request.DateRequest;
import com.linklyze.visual.api.v1.request.PageRequest;
import com.linklyze.visual.model.RefererGroupByDO;
import com.linklyze.visual.model.TrendGroupByDO;
import com.linklyze.visual.vo.RegionStatsVO;
import com.linklyze.visual.vo.StatsListVO;
import com.linklyze.visual.model.DwsWideInfo;
import com.linklyze.visual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 访问统计接口
 *
 * @author novo
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/visual/v1")
@Validated
public class AccessController {

    @Autowired
    private AccessService accessService;


    /**
     * 分页查询实时访问记录
     *
     * @param pageRequest
     */
    @PostMapping("/page")
    public Response<PageResponse<DwsWideInfo>> page(@RequestBody @Validated PageRequest pageRequest) {
        int total = pageRequest.getPage() * pageRequest.getSize();
        if (total > Constants.VISUAL_MAX_LIMIT) {
            return Response.error("只允许查询最近1000条数据");
        }
        PageResponse<DwsWideInfo> page = this.accessService.page(pageRequest);
        return Response.success(page);
    }

    /**
     * 区域PV、UV统计
     *
     * @return
     */
    @PostMapping("/region")
    public Response<List<RegionStatsVO>> region(@RequestBody @Validated DateRequest dateRequest) {
        List<RegionStatsVO> regionStatsVOList = this.accessService.region(dateRequest);
        return Response.success(regionStatsVOList);
    }

    /**
     * Device/OS/Browser类型 PV、UV统计
     *
     * @return
     */
    @PostMapping("/type")
    public Response<StatsListVO> type(@RequestBody @Validated DateRequest dateRequest) {
        StatsListVO statsListVO = this.accessService.type(dateRequest);
        return Response.success(statsListVO);
    }

    /**
     * 访问趋势图(天/小时)
     *
     * @param dateRequest
     * @return
     */
    @PostMapping("/trend")
    public Response<List<TrendGroupByDO>> trend(@RequestBody @Validated DateRequest dateRequest) {
        List<TrendGroupByDO> trendList = this.accessService.trend(dateRequest);
        List<TrendGroupByDO> res = trendList.stream().peek(trendGroupByDO -> {
            if (trendGroupByDO.getType() == TrendIntervalType.DAY) {
                trendGroupByDO.setInterval(TimeUtil.format(trendGroupByDO.getInterval(), TimeUtil.YYMMDD_PATTERN, TimeUtil.YY_MM_DD_PATTERN));
            }
        }).collect(Collectors.toList());
        return Response.success(res);
    }


    /**
     * 访问来源TopN统计
     *
     * @param dateRequest
     * @return
     */
    @PostMapping("/referer")
    public Response<List<RefererGroupByDO>> refererTopN(@RequestBody @Validated DateRequest dateRequest) {
        List<RefererGroupByDO> refererTopN = this.accessService.refererTopN(dateRequest);
        return Response.success(refererTopN);
    }
}
