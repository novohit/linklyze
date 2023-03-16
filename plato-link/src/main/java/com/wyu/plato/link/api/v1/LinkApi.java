package com.wyu.plato.link.api.v1;

import com.wyu.plato.common.util.CheckUtil;
import com.wyu.plato.link.model.ShortLinkDO;
import com.wyu.plato.link.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author novo
 * @since 2023-03-16
 */
@Controller
public class LinkApi {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 短链码跳转
     *
     * @param code
     * @param response
     */
    @GetMapping("/{code}")
    @ResponseBody
    public void dispatch(@PathVariable("code") String code, HttpServletResponse response) {
        /**
         * 什么要用 301 跳转而不是 302
         * 301 是永久重定向，302 是临时重定向。
         * 短地址一经生成就不会变化，所以用 301 是同时对服务器压力也会有一定减少
         * 但是如果使用了 301，无法统计到短地址被点击的次数。
         * 所以选择302虽然会增加服务器压力，但是有很多数据可以获取进行分析
         */
        // 判断短链码是否合法
        if (CheckUtil.isLetterOrDigit(code)) {
            ShortLinkDO shortLink = this.shortLinkService.findOneByCode(code);
            if (shortLink != null) {
                response.setHeader("Location", shortLink.getOriginalUrl());
                // 302跳转
                response.setStatus(HttpStatus.FOUND.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }
}
