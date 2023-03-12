package com.wyu.plato.link.service.impl;

import com.wyu.plato.common.LocalUserThreadHolder;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.link.api.v1.request.LinkGroupCreateRequest;
import com.wyu.plato.link.model.LinkGroupDO;
import com.wyu.plato.link.mapper.LinkGroupMapper;
import com.wyu.plato.link.service.LinkGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-03-11
 */
@Service
public class LinkGroupServiceImpl extends ServiceImpl<LinkGroupMapper, LinkGroupDO> implements LinkGroupService {

    @Autowired
    private LinkGroupMapper linkGroupMapper;
    @Override
    public void create(LinkGroupCreateRequest createRequest) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        LinkGroupDO linkGroupDO = new LinkGroupDO();
        linkGroupDO.setTitle(createRequest.getTitle());
        linkGroupDO.setAccountNo(accountNo);
        int rows = linkGroupMapper.insert(linkGroupDO);
        if (rows <= 0) {
            throw new BizException(BizCodeEnum.GROUP_CREATE_ERROR);
        }
    }
}
