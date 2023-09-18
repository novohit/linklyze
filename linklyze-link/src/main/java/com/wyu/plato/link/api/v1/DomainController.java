package com.wyu.plato.link.api.v1;


import com.linklyze.common.model.vo.Resp;
import com.wyu.plato.link.model.DomainDO;
import com.wyu.plato.link.service.DomainService;
import com.wyu.plato.link.vo.DomainVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 域名接口
 *
 * @author novo
 * @since 2023-03-16
 */
@RestController
@RequestMapping("/domain/v1")
public class DomainController {

    @Autowired
    private DomainService domainService;

    /**
     * 可用域名列表
     *
     * @return
     */
    @GetMapping("/list")
    public Resp<List<DomainDO>> findAll() {
        List<DomainDO> domainList = this.domainService.findAll();
        List<DomainVO> domainVOList = domainList.stream().map(domainDO -> {
            DomainVO domainVO = new DomainVO();
            BeanUtils.copyProperties(domainDO, domainVO);
            return domainVO;
        }).collect(Collectors.toList());
        return Resp.success(domainList);
    }
}
