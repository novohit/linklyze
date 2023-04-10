package com.wyu.plato.visual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.common.enums.TrendIntervalType;
import com.wyu.plato.common.util.TimeUtil;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.DateRequest;
import com.wyu.plato.visual.mapper.AccessMapper;
import com.wyu.plato.visual.model.RefererGroupByDO;
import com.wyu.plato.visual.model.TypeGroupByDO;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.model.TrendGroupByDO;
import com.wyu.plato.visual.service.AccessService;
import com.wyu.plato.visual.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 加上accountno 避免越权
 *
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
    public List<RegionStatsVO> region(DateRequest dateRequest) {
        // TODO 查询区间不能过大
        String start = TimeUtil.format(dateRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(dateRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        return this.accessMapper.region(dateRequest.getCode(), start, end);
    }

    @Override
    public StatsListVO type(DateRequest dateRequest) {
        // TODO 查询区间不能过大
        String start = TimeUtil.format(dateRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(dateRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        List<TypeGroupByDO> list = this.accessMapper.type(dateRequest.getCode(), start, end);

        // 先计算总和
        long pvSum = 0;
        long uvSum = 0;
        for (TypeGroupByDO group : list) {
            pvSum += group.getPv();
            uvSum += group.getUv();
        }
        final double finalPvSum = pvSum;
        final double finalUvSum = uvSum;
        // 单字段分组 多字段求和用reduce 如果是单字段求和Collectors.groupingBy(DeviceGroupByDO::getBrowserType,Collectors.summarizingLong(DeviceGroupByDO::getPv))
        // 浏览器分组
        List<BrowserStats> browserStatsList = list.stream()
                .collect(Collectors.groupingBy(TypeGroupByDO::getBrowserType, Collectors.reducing((a, b) -> {
                    TypeGroupByDO res = new TypeGroupByDO();
                    res.setBrowserType(a.getBrowserType());
                    res.setPv(a.getPv() + b.getPv());
                    res.setUv(a.getUv() + b.getUv());
                    return res;
                })))
                // 分组reduce后返回的是Map<group_key, obj>
                // 我们拿到obj再重新组装一下
                .values().stream().map(optional -> {
                    if (optional.isPresent()) {
                        BrowserStats browserStats = new BrowserStats();
                        TypeGroupByDO group = optional.get();
                        browserStats.setBrowser(group.getBrowserType());
                        browserStats.setPv(group.getPv());
                        browserStats.setUv(group.getUv());
                        browserStats.setPvRatio(group.getPv() / finalPvSum);
                        browserStats.setUvRatio(group.getUv() / finalUvSum);
                        return browserStats;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());

        // 操作系统分组
        List<OsStats> osStatsList = list.stream()
                .collect(Collectors.groupingBy(TypeGroupByDO::getBrowserType, Collectors.reducing((a, b) -> {
                    TypeGroupByDO res = new TypeGroupByDO();
                    res.setOs(a.getOs());
                    res.setPv(a.getPv() + b.getPv());
                    res.setUv(a.getUv() + b.getUv());
                    return res;
                })))
                // 分组reduce后返回的是Map<group_key, obj>
                // 我们拿到obj再重新组装一下
                .values().stream().map(optional -> {
                    if (optional.isPresent()) {
                        OsStats osStats = new OsStats();
                        TypeGroupByDO group = optional.get();
                        osStats.setOs(group.getOs());
                        osStats.setPv(group.getPv());
                        osStats.setUv(group.getUv());
                        osStats.setPvRatio(group.getPv() / finalPvSum);
                        osStats.setUvRatio(group.getUv() / finalUvSum);
                        return osStats;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());

        // 设备类型分组
        List<DeviceStats> deviceStatsList = list.stream()
                .collect(Collectors.groupingBy(TypeGroupByDO::getDeviceType, Collectors.reducing((a, b) -> {
                    TypeGroupByDO res = new TypeGroupByDO();
                    res.setDeviceType(a.getDeviceType());
                    res.setPv(a.getPv() + b.getPv());
                    res.setUv(a.getUv() + b.getUv());
                    return res;
                })))
                // 分组reduce后返回的是Map<group_key, obj>
                // 我们拿到obj再重新组装一下
                .values().stream().map(optional -> {
                    if (optional.isPresent()) {
                        DeviceStats deviceStats = new DeviceStats();
                        TypeGroupByDO group = optional.get();
                        deviceStats.setDeviceType(group.getDeviceType());
                        deviceStats.setPv(group.getPv());
                        deviceStats.setUv(group.getUv());
                        deviceStats.setPvRatio(group.getPv() / finalPvSum);
                        deviceStats.setUvRatio(group.getUv() / finalUvSum);
                        return deviceStats;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());

        StatsListVO res = new StatsListVO(browserStatsList, osStatsList, deviceStatsList, pvSum, uvSum);
        return res;
    }

    @Override
    public List<TrendGroupByDO> trend(DateRequest dateRequest) {
        // TODO 查询区间不能过大
        String start = TimeUtil.format(dateRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(dateRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        List<TrendGroupByDO> trendList;
        // 查询一天内
        if (start.equals(end)) {
            trendList = this.accessMapper.trendByHour(dateRequest.getCode(), start);
            trendList = hourlyStats(trendList);
        } else {
            trendList = this.accessMapper.trendByDay(dateRequest.getCode(), start, end);
            trendList = dailyStats(trendList, start, end);
        }

        return trendList;
    }

    @Override
    public List<RefererGroupByDO> refererTopN(DateRequest dateRequest) {
        String start = TimeUtil.format(dateRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(dateRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        return this.accessMapper.refererTopN(dateRequest.getCode(), start, end, dateRequest.getN());
    }

    /**
     * 连续日期的补全
     *
     * @param dbList
     * @param start
     * @param end
     * @return
     */
    private List<TrendGroupByDO> dailyStats(List<TrendGroupByDO> dbList, String start, String end) {
        List<TrendGroupByDO> runningDays = new ArrayList<>();
        // 连续日期趋势
        List<String> calendar = TimeUtil.getCalendar(start, end, TimeUtil.YYMMDD_PATTERN);
        for (String date : calendar) {
            boolean noData = true;
            for (TrendGroupByDO trend : dbList) {
                if (trend.getInterval().equals(date)) {
                    noData = false;
                    trend.setType(TrendIntervalType.DAY);
                    runningDays.add(trend);
                    break;
                }
            }
            // 数据库中没查到 设置默认数据
            if (noData) {
                TrendGroupByDO trend = new TrendGroupByDO();
                trend.setInterval(date);
                trend.setType(TrendIntervalType.DAY);
                runningDays.add(trend);
            }
        }
        return runningDays;
    }

    /**
     * 连续时间的补全
     *
     * @param dbList
     * @return
     */
    private List<TrendGroupByDO> hourlyStats(List<TrendGroupByDO> dbList) {
        List<TrendGroupByDO> runningHour = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            boolean noData = true;
            for (TrendGroupByDO trend : dbList) {
                if (trend.getInterval().equals(String.valueOf(i))) {
                    noData = false;
                    trend.setType(TrendIntervalType.HOUR);
                    runningHour.add(trend);
                    break;
                }
            }
            // 数据库中没查到 设置默认数据
            if (noData) {
                TrendGroupByDO trend = new TrendGroupByDO();
                trend.setInterval(String.valueOf(i));
                trend.setType(TrendIntervalType.HOUR);
                runningHour.add(trend);
            }
        }
        return runningHour;
    }
}
