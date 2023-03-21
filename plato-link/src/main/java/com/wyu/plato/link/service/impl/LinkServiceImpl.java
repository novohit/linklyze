package com.wyu.plato.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.common.enums.LinkStateEnum;
import com.wyu.plato.link.mapper.LinkMapper;
import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author novo
 * @since 2023-03-11
 */
@Service
@Slf4j
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDO> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public LinkDO findOneByCode(String code) {
        LinkDO linkDO = this.linkMapper
                .selectOne(new QueryWrapper<LinkDO>().lambda().eq(LinkDO::getCode, code));
        if (linkDO == null) {
            log.info("link is null,code:[{}]", code);
            return null;
        }
        // 校验短链码的状态和是否过期
        if (!linkDO.getState().equals(LinkStateEnum.LOCK.name())) {
            log.info("link is lock:[{}]", linkDO);
            return null;
        }
        if (linkDO.getExpired().before(new Date())) {
            log.info("link is expired:[{}]", linkDO);
            return null;
        }
        return linkDO;
    }
}
