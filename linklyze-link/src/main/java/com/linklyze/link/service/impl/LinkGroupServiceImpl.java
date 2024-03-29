package com.linklyze.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.LocalUserThreadHolder;
import com.linklyze.common.enums.BizCodeEnum;
import com.linklyze.common.exception.BizException;
import com.linklyze.link.mapper.LinkGroupMapper;
import com.linklyze.link.model.LinkGroupDO;
import com.linklyze.link.api.v1.request.GroupCreateRequest;
import com.linklyze.link.api.v1.request.GroupUpdateRequest;
import com.linklyze.link.manager.LinkGroupManager;
import com.linklyze.link.service.LinkGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-11
 */
@Service
public class LinkGroupServiceImpl extends ServiceImpl<LinkGroupMapper, LinkGroupDO> implements LinkGroupService {

    @Autowired
    private LinkGroupManager linkGroupManager;

    @Override
    public void create(GroupCreateRequest createRequest) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        LinkGroupDO linkGroupDO = new LinkGroupDO();
        linkGroupDO.setTitle(createRequest.getTitle());
        linkGroupDO.setAccountNo(accountNo);
        int rows = this.linkGroupManager.insert(linkGroupDO);
        if (rows <= 0) {
            throw new BizException(BizCodeEnum.GROUP_CREATE_ERROR);
        }
    }

    @Override
    public void delete(Long groupId) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        int rows = this.linkGroupManager.deleteGroup(groupId, accountNo);
        if (rows <= 0) {
            throw new BizException(BizCodeEnum.GROUP_DELETE_ERROR);
        }
    }

    @Override
    public LinkGroupDO findOne(Long groupId) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        return this.linkGroupManager.findById(groupId, accountNo);
    }

    @Override
    public List<LinkGroupDO> findAll() {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        return this.linkGroupManager.findAll(accountNo);
    }

    @Override
    public void update(GroupUpdateRequest updateRequest) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        LinkGroupDO groupDO = new LinkGroupDO();
        BeanUtils.copyProperties(updateRequest, groupDO);
        int rows = this.linkGroupManager.update(groupDO, accountNo);
        if (rows <= 0) {
            throw new BizException(BizCodeEnum.GROUP_OPER_ERROR);
        }
    }
}
