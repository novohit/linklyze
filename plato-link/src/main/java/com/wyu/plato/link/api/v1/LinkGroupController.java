package com.wyu.plato.link.api.v1;


import com.wyu.plato.common.util.Resp;
import com.wyu.plato.link.api.v1.request.LinkGroupCreateRequest;
import com.wyu.plato.link.api.v1.request.LinkGroupUpdateRequest;
import com.wyu.plato.link.model.LinkGroupDO;
import com.wyu.plato.link.service.LinkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-03-11
 */
@RestController
@RequestMapping("/group/v1")
@Validated
public class LinkGroupController {

    @Autowired
    private LinkGroupService linkGroupService;

    /**
     * 创建短链分组
     *
     * @param createRequest
     * @return
     */
    @PostMapping
    public Resp create(@RequestBody @Validated LinkGroupCreateRequest createRequest) {
        this.linkGroupService.create(createRequest);
        return Resp.success();
    }

    /**
     * 删除分组
     *
     * @param groupId
     * @return
     */
    @DeleteMapping("/{group_id}")
    public Resp delete(@PathVariable("group_id") @Positive Long groupId) {
        this.linkGroupService.delete(groupId);
        return Resp.success();
    }

    /**
     * 更新分组
     *
     * @param updateRequest
     * @return
     */
    @PutMapping
    public Resp update(@RequestBody @Validated LinkGroupUpdateRequest updateRequest) {
        this.linkGroupService.update(updateRequest);
        return Resp.success();
    }

    /**
     * 分组详情
     *
     * @param groupId
     * @return
     */
    @GetMapping("/{group_id}")
    public Resp findOne(@PathVariable("group_id") @Positive Long groupId) {
        return Resp.success(this.linkGroupService.findOne(groupId));
    }

    /**
     * 分组列表
     *
     * @return
     */
    @GetMapping("/list")
    public Resp findAll() {
        return Resp.success(this.linkGroupService.findAll());
    }
}
