package com.wyu.plato.link.api.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.common.model.vo.PageVO;
import com.linklyze.common.model.vo.Resp;
import com.linklyze.common.util.CommonUtil;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.api.v1.request.LinkDeleteRequest;
import com.wyu.plato.link.api.v1.request.LinkUpdateRequest;
import com.wyu.plato.link.api.v1.request.PageRequest;
import com.wyu.plato.link.model.LinkMappingDO;
import com.wyu.plato.link.service.LinkService;
import com.wyu.plato.link.vo.LinkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 短链接口
 *
 * @author novo
 * @since 2023-03-11
 */
@RestController
@RequestMapping("/link/v1")
@Validated
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    @Qualifier(value = "lock")
    private RedisScript<Long> lockRedisScript;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 短链创建
     *
     * @param linkCreateRequest
     * @return
     */
    @PostMapping
    public Resp<Void> create(@RequestBody @Validated LinkCreateRequest linkCreateRequest) {
        this.linkService.create(linkCreateRequest);
        return Resp.success();
    }

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    @PostMapping("/page")
    public Resp<PageVO<LinkVO>> page(@RequestBody @Validated PageRequest pageRequest) {
        Page<LinkMappingDO> page = this.linkService.page(pageRequest);
        List<LinkMappingDO> records = page.getRecords();

        List<LinkVO> linkVOList = records.stream()
                .map(mappingDO -> {
                    LinkVO linkVO = new LinkVO();
                    BeanUtils.copyProperties(mappingDO, linkVO);
                    linkVO.setOriginalUrl(CommonUtil.removeUrlPrefix(linkVO.getOriginalUrl()));
                    return linkVO;
                }).collect(Collectors.toList());


        PageVO<LinkVO> pageVO = new PageVO<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), linkVOList);
        return Resp.success(pageVO);
    }


    /**
     * 短链更新
     *
     * @param linkUpdateRequest
     * @return
     */
    @PutMapping
    public Resp<Void> update(@RequestBody @Validated LinkUpdateRequest linkUpdateRequest) {
        this.linkService.update(linkUpdateRequest);
        return Resp.success();
    }

    /**
     * 短链删除
     *
     * @param linkDeleteRequest
     * @return
     */
    @DeleteMapping
    public Resp<Void> delete(@RequestBody @Validated LinkDeleteRequest linkDeleteRequest) {
        this.linkService.delete(linkDeleteRequest);
        return Resp.success();
    }

    /**
     * 分布式可重入锁测试
     *
     * @param code
     * @param accountNo
     * @return
     */
    @GetMapping("/test-lock")
    public Resp<Long> testCreate(@RequestParam("code") String code, @RequestParam("account_no") Long accountNo) {
        Long res = this.redisTemplate.execute(lockRedisScript, Collections.singletonList(code), accountNo, 100);
        return Resp.success(res);
    }

}
