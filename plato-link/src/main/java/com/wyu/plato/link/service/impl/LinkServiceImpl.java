package com.wyu.plato.link.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.common.LocalUserThreadHolder;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.LinkLevelType;
import com.wyu.plato.common.enums.LinkStateEnum;
import com.wyu.plato.common.enums.MessageEventType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.CustomMessage;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.common.util.uuid.IDUtil;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.component.ShortLinkComponent;
import com.wyu.plato.link.config.RabbitMQConfig;
import com.wyu.plato.link.manager.DomainManager;
import com.wyu.plato.link.manager.LinkGroupManager;
import com.wyu.plato.link.manager.LinkManager;
import com.wyu.plato.link.manager.LinkMappingManager;
import com.wyu.plato.link.mapper.LinkMapper;
import com.wyu.plato.link.model.DomainDO;
import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.model.LinkGroupDO;
import com.wyu.plato.link.model.LinkMappingDO;
import com.wyu.plato.link.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private LinkMappingManager linkMappingManager;

    @Autowired
    private DomainManager domainManager;

    @Autowired
    private LinkGroupManager linkGroupManager;

    @Autowired
    private ShortLinkComponent shortLinkComponent;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    @Qualifier("lock")
    private RedisScript<Long> lockScript;

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

    /**
     * 生产者端创建逻辑
     *
     * @param request
     */
    @Override
    public void create(LinkCreateRequest request) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        // 1.校验域名
        DomainDO domain = this.domainManager.findById(request.getDomainId(), accountNo);
        if (domain == null) {
            throw new BizException(BizCodeEnum.DOMAIN_NOT_EXIST);
        }
        // 将域名set进去
        request.setDomain(domain.getValue());
        // 2.校验分组
        LinkGroupDO group = this.linkGroupManager.findById(request.getGroupId(), accountNo);
        if (group == null) {
            throw new BizException(BizCodeEnum.GROUP_NOT_EXIST);
        }

        // 3.给原始url添加时间戳前缀
        request.setOriginalUrl(CommonUtil.addUrlPrefix(request.getOriginalUrl()));

        // 4.构造自定义MQ消息对象
        CustomMessage message = CustomMessage.builder().messageId(IDUtil.fastUUID()).accountNo(accountNo).eventType(MessageEventType.LINK_CREATE).content(JSON.toJSONString(request)).build();

        // 5.向MQ发送消息
        log.info("向MQ发送消息,message:[{}]", message);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.LINK_EVENT_EXCHANGE, RabbitMQConfig.CREATE_LINK_ROUTING_KEY, message);
    }


    /**
     * 消费者端创建短链逻辑
     *
     * @param customMessage
     */
    public void handleCreate(CustomMessage customMessage) {
        // 1. 生成短链
        Long accountNo = customMessage.getAccountNo();
        LinkCreateRequest request = JSON.parseObject(customMessage.getContent(), LinkCreateRequest.class);
        ShortLinkComponent.Link shortLink = this.shortLinkComponent.createShortLink(request.getOriginalUrl());
        String code = shortLink.getCode();
        long hash32 = shortLink.getHash32();

        // 短链码冲突或者加锁失败标记
        boolean conflict = false;
        // TODO 2. 加锁
        Long res = this.redisTemplate.execute(lockScript, Collections.singletonList(code), accountNo, 100);

        if (res > 0) {
            // 加锁成功
            switch (customMessage.getEventType()) {
                // C端
                case LINK_CREATE: {
                    log.info("C端加锁成功");
                    // 3. 查询数据库中是否存在该短链
                    LinkDO dbLink = this.linkManager.findOneByCode(code);
                    if (dbLink == null) {
                        // 4. 构造入库对象
                        LinkDO linkDO = this.genLinkDO(request, accountNo, code, hash32);
                        // 5. 入库
                        int rows = this.linkManager.save(linkDO);
                    } else {
                        // 数据库已存在该短链码
                        log.warn("C端短链码冲突");
                        conflict = true;
                    }
                    break;
                }
                // B端
                case LINK_MAPPING_CREATE: {
                    log.info("B端加锁成功");
                    // 3. 查询数据库中是否存在该短链
                    LinkMappingDO dbLinkMapping = this.linkMappingManager.findOneByCode(code);
                    if (dbLinkMapping == null) {
                        // 4. 构造入库对象
                        LinkMappingDO mappingDO = this.genLinkMappingDO(request, accountNo, code, hash32);
                        // 5. 入库
                        int rows = this.linkMappingManager.save(mappingDO);
                    } else {
                        // 数据库已存在该短链码
                        log.warn("B端短链码冲突");
                        conflict = true;
                    }
                    break;
                }
                default:
                    throw new BizException(BizCodeEnum.SERVER_ERROR);
            }
        } else {
            // 加锁失败 开始自旋
            log.warn("加锁失败 开始自旋 message:[{}]", customMessage);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            conflict = true;
        }

        if (conflict) {
            // 更新长链版本号
            String newUrl = CommonUtil.getNewUrl(request.getOriginalUrl());
            request.setOriginalUrl(newUrl);
            customMessage.setContent(JSON.toJSONString(request));
            // 开始递归
            // TODO 限制递归深度
            this.handleCreate(customMessage);
        }
        // TODO 解锁
    }


    /**
     * 构造mapping入库对象
     *
     * @param request
     * @param accountNo
     * @param code
     * @param hash32
     * @return
     */
    private LinkMappingDO genLinkMappingDO(LinkCreateRequest request, Long accountNo, String code, long hash32) {
        LinkMappingDO mappingDO = new LinkMappingDO();
        BeanUtils.copyProperties(request, mappingDO);
        mappingDO.setAccountNo(accountNo);
        mappingDO.setCode(code);
        // TODO 数据库字段类型修改
        mappingDO.setLongHash(String.valueOf(hash32));
        mappingDO.setState(LinkStateEnum.ACTIVE.name());
        // TODO 查询短链level
        mappingDO.setLinkLevel(LinkLevelType.BRONZE.name());
        return mappingDO;
    }

    /**
     * 构造link入库对象
     *
     * @param request
     * @param accountNo
     * @param code
     * @param hash32
     * @return
     */
    private LinkDO genLinkDO(LinkCreateRequest request, Long accountNo, String code, long hash32) {
        LinkDO linkDO = new LinkDO();
        BeanUtils.copyProperties(request, linkDO);
        linkDO.setAccountNo(accountNo);
        linkDO.setCode(code);
        // TODO 数据库字段类型修改
        linkDO.setLongHash(String.valueOf(hash32));
        linkDO.setState(LinkStateEnum.ACTIVE.name());
        // TODO 查询短链level
        linkDO.setLinkLevel(LinkLevelType.BRONZE.name());
        return linkDO;
    }
}
