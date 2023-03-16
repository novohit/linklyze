package com.wyu.plato.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.common.enums.LinkStateEnum;
import com.wyu.plato.link.mapper.ShortLinkMapper;
import com.wyu.plato.link.model.ShortLinkDO;
import com.wyu.plato.link.service.ShortLinkService;
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
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Autowired
    private ShortLinkMapper shortLinkMapper;

    @Override
    public ShortLinkDO findOneByCode(String code) {
        ShortLinkDO shortLinkDO = this.shortLinkMapper
                .selectOne(new QueryWrapper<ShortLinkDO>().lambda().eq(ShortLinkDO::getCode, code));
        if (shortLinkDO == null) {
            log.info("link is null,code:[{}]", code);
            return null;
        }
        // 校验短链码的状态和是否过期
        if (!shortLinkDO.getState().equals(LinkStateEnum.LOCK.name())) {
            log.info("link is lock:[{}]", shortLinkDO);
            return null;
        }
        if (shortLinkDO.getExpired().before(new Date())) {
            log.info("link is expired:[{}]", shortLinkDO);
            return null;
        }
        return shortLinkDO;
    }
}
