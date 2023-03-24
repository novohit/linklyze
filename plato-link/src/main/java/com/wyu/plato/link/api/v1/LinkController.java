package com.wyu.plato.link.api.v1;


import com.wyu.plato.common.util.Resp;
import com.wyu.plato.link.api.v1.request.LinkCreateRequest;
import com.wyu.plato.link.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
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

    @GetMapping("/test-uu")
    public void testUU(HttpServletResponse response) {
        System.out.println(response);
    }

    /**
     * 创建短链
     *
     * @param linkCreateRequest
     * @return
     */
    @PostMapping
    public Resp create(@RequestBody @Validated LinkCreateRequest linkCreateRequest) {
        this.linkService.create(linkCreateRequest);
        return Resp.success();
    }

    /**
     * 分布式可重入锁测试
     *
     * @param code
     * @param accountNo
     * @return
     */
    @GetMapping("/test-create-link")
    public Resp testCreate(@RequestParam("code") String code, @RequestParam("account_no") Long accountNo) {
        Long res = this.redisTemplate.execute(lockRedisScript, Collections.singletonList(code), accountNo, 100);
        return Resp.success(res);
    }

}
