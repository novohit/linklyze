package com.wyu.plato.visual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.common.util.TimeUtil;
import com.wyu.plato.visual.api.v1.request.DeviceRequest;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.RegionRequest;
import com.wyu.plato.visual.mapper.AccessMapper;
import com.wyu.plato.visual.model.DeviceGroupByDO;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.service.AccessService;
import com.wyu.plato.visual.vo.BrowserStats;
import com.wyu.plato.visual.vo.DeviceGroupVO;
import com.wyu.plato.visual.vo.OsStats;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public DeviceGroupVO device(DeviceRequest deviceRequest) {
        // TODO 查询区间不能过大
        String start = TimeUtil.format(deviceRequest.getStart(), TimeUtil.YYMMDD_PATTERN);
        String end = TimeUtil.format(deviceRequest.getEnd(), TimeUtil.YYMMDD_PATTERN);
        List<DeviceGroupByDO> list = this.accessMapper.device(deviceRequest.getCode(), start, end);

        // 先计算总和
        long pvSum = 0;
        long uvSum = 0;
        for (DeviceGroupByDO group : list) {
            pvSum += group.getPv();
            uvSum += group.getUv();
        }
        final double finalPvSum = pvSum;
        final double finalUvSum = uvSum;
        // 单字段分组 多字段求和用reduce 如果是单字段求和Collectors.groupingBy(DeviceGroupByDO::getBrowserType,Collectors.summarizingLong(DeviceGroupByDO::getPv))
        List<BrowserStats> browserStatsList = list.stream()
                .collect(Collectors.groupingBy(DeviceGroupByDO::getBrowserType, Collectors.reducing((a, b) -> {
                    DeviceGroupByDO res = new DeviceGroupByDO();
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
                        DeviceGroupByDO group = optional.get();
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

        List<OsStats> osStatsList = list.stream()
                .collect(Collectors.groupingBy(DeviceGroupByDO::getBrowserType, Collectors.reducing((a, b) -> {
                    DeviceGroupByDO res = new DeviceGroupByDO();
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
                        DeviceGroupByDO group = optional.get();
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

        DeviceGroupVO res = new DeviceGroupVO(browserStatsList, osStatsList, pvSum, uvSum);
        return res;
    }
}
