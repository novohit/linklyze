package com.wyu.plato.link.api.v1;


import com.wyu.plato.common.util.Resp;
import com.wyu.plato.link.api.v1.request.LinkGroupCreateRequest;
import com.wyu.plato.link.model.LinkGroupDO;
import com.wyu.plato.link.service.LinkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author novo
 * @since 2023-03-11
 */
@RestController
@RequestMapping("/group/v1")
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

    @DeleteMapping("/{group_id}")
    public Resp delete(@PathVariable("group_id") Long groupId) {
        this.linkGroupService.delete(groupId);
        return Resp.success();
    }

    @GetMapping("/{group_id}")
    public Resp findOne(@PathVariable("group_id") Long groupId) {
        return Resp.success(this.linkGroupService.findOne(groupId));
    }
}
