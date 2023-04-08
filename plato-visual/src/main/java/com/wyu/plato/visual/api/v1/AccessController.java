package com.wyu.plato.visual.api.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyu.plato.common.model.vo.PageVO;
import com.wyu.plato.common.model.vo.Resp;
import com.wyu.plato.visual.api.v1.request.PageRequest;
import com.wyu.plato.visual.api.v1.request.RegionRequest;
import com.wyu.plato.visual.model.DwsWideInfo;
import com.wyu.plato.visual.service.AccessService;
import com.wyu.plato.visual.vo.RegionStatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Resp page(@RequestBody @Validated PageRequest pageRequest) {
        Page<DwsWideInfo> page = this.accessService.page(pageRequest);
        PageVO<DwsWideInfo> pageVO = new PageVO<>(page);
        return Resp.success(pageVO);
    }

    /**
     * 区域pv、uv统计
     *
     * @return
     */
    @PostMapping("/region")
    public Resp region(@RequestBody @Validated RegionRequest regionRequest) {
        List<RegionStatsVO> regionStatsVOList = this.accessService.region(regionRequest);
        return Resp.success(regionStatsVOList);
    }
}
