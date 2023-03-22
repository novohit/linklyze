package com.wyu.plato.link.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.common.LocalUserThreadHolder;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.LinkStateEnum;
import com.wyu.plato.common.enums.MessageEventType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.CustomMessage;
import com.wyu.plato.common.util.uuid.IDUtil;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.config.RabbitMQConfig;
import com.wyu.plato.link.manager.DomainManager;
import com.wyu.plato.link.manager.LinkGroupManager;
import com.wyu.plato.link.manager.LinkManager;
import com.wyu.plato.link.mapper.LinkMapper;
import com.wyu.plato.link.model.DomainDO;
import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.model.LinkGroupDO;
import com.wyu.plato.link.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private LinkManager linkManager;

    @Autowired
    private DomainManager domainManager;

    @Autowired
    private LinkGroupManager linkGroupManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public LinkDO findOneByCode(String code) {
        LinkDO linkDO = this.linkManager.findOneByCode(code);
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

    @Override
    public void create(LinkCreateRequest request) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        // 1.校验域名
        DomainDO domain = this.domainManager.findById(request.getDomainId(), accountNo);
        if (domain == null) {
            throw new BizException(BizCodeEnum.DOMAIN_NOT_EXIST);
        }
        // 2.校验分组
        LinkGroupDO group = this.linkGroupManager.findById(request.getGroupId(), accountNo);
        if (group == null) {
            throw new BizException(BizCodeEnum.GROUP_NOT_EXIST);
        }
        // 3.构造自定义MQ消息对象
        CustomMessage message = CustomMessage.builder()
                .messageId(IDUtil.fastUUID())
                .accountNo(accountNo)
                .eventType(MessageEventType.LINK_CREATE.name())
                .content(JSON.toJSONString(request))
                .build();

        // 4.向MQ发送消息
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.LINK_EVENT_EXCHANGE, RabbitMQConfig.CREATE_LINK_ROUTING_KEY, message);
    }
}
