package com.wyu.plato.visual.api.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.common.enums.TrendIntervalType;
import com.wyu.plato.common.model.vo.PageVO;
import com.wyu.plato.common.model.vo.Resp;
import com.wyu.plato.common.util.TimeUtil;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.DateRequest;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.model.RefererGroupByDO;
import com.wyu.plato.visual.model.TrendGroupByDO;
import com.wyu.plato.visual.service.AccessService;
import com.wyu.plato.visual.vo.StatsListVO;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
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
    public Resp<PageVO<DwsWideInfo>> page(@RequestBody @Validated PageRequest pageRequest) {
        Page<DwsWideInfo> page = this.accessService.page(pageRequest);
        PageVO<DwsWideInfo> pageVO = new PageVO<>(page);
        return Resp.success(pageVO);
    }

    /**
     * 区域PV、UV统计
     *
     * @return
     */
    @PostMapping("/region")
    public Resp<List<RegionStatsVO>> region(@RequestBody @Validated DateRequest dateRequest) {
        List<RegionStatsVO> regionStatsVOList = this.accessService.region(dateRequest);
        return Resp.success(regionStatsVOList);
    }

    /**
     * Device/OS/Browser类型 PV、UV统计
     *
     * @return
     */
    @PostMapping("/type")
    public Resp<StatsListVO> type(@RequestBody @Validated DateRequest dateRequest) {
        StatsListVO statsListVO = this.accessService.type(dateRequest);
        return Resp.success(statsListVO);
    }

    /**
     * 访问趋势图(天/小时)
     *
     * @param dateRequest
     * @return
     */
    @PostMapping("/trend")
    public Resp<List<TrendGroupByDO>> trend(@RequestBody @Validated DateRequest dateRequest) {
        List<TrendGroupByDO> trendList = this.accessService.trend(dateRequest);
        List<TrendGroupByDO> res = trendList.stream().peek(trendGroupByDO -> {
            if (trendGroupByDO.getType() == TrendIntervalType.DAY) {
                trendGroupByDO.setInterval(TimeUtil.format(trendGroupByDO.getInterval(), TimeUtil.YYMMDD_PATTERN, TimeUtil.YY_MM_DD_PATTERN));
            }
        }).collect(Collectors.toList());
        return Resp.success(res);
    }


    /**
     * 访问来源TopN统计
     *
     * @param dateRequest
     * @return
     */
    @PostMapping("/referer")
    public Resp<List<RefererGroupByDO>> refererTopN(@RequestBody @Validated DateRequest dateRequest) {
        List<RefererGroupByDO> refererTopN = this.accessService.refererTopN(dateRequest);
        return Resp.success(refererTopN);
    }
}
