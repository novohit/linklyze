package com.linklyze.link.api.v1;


import com.linklyze.common.model.vo.Resp;
import com.linklyze.link.api.v1.request.GroupCreateRequest;
import com.linklyze.link.api.v1.request.GroupUpdateRequest;
import com.linklyze.link.model.LinkGroupDO;
import com.linklyze.link.service.LinkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 分组接口
 *
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
     * 创建分组
     *
     * @param createRequest
     * @return
     */
    @PostMapping
    public Resp<Void> create(@RequestBody @Validated GroupCreateRequest createRequest) {
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
    public Resp<Void> delete(@PathVariable("group_id") @Positive Long groupId) {
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
    public Resp<Void> update(@RequestBody @Validated GroupUpdateRequest updateRequest) {
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
    public Resp<LinkGroupDO> findOne(@PathVariable("group_id") @Positive Long groupId) {
        LinkGroupDO group = this.linkGroupService.findOne(groupId);
        return Resp.success(group);
    }

    /**
     * 分组列表
     *
     * @return
     */
    @GetMapping("/list")
    public Resp<List<LinkGroupDO>> findAll() {
        List<LinkGroupDO> groupList = this.linkGroupService.findAll();
        return Resp.success(groupList);
    }
}
