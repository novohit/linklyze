package com.wyu.plato.link.api.v1;

import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.util.CheckUtil;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.link.model.LinkDO;
import com.wyu.plato.link.service.LinkService;
import com.wyu.plato.link.service.LogService;
import com.wyu.plato.link.strategy.ShardingConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * C端短链解析接口
 *
 * @author novo
 * @since 2023-03-16
 */
@Controller
@RequestMapping
@Slf4j
public class LinkApi {

    @Autowired
    private LinkService linkService;

    @Autowired
    private LogService logService;

    /**
     * 短链码跳转
     *
     * @param code
     * @param response
     */
    @GetMapping("/{code}")
    public void dispatch(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response) {
        log.info("code:[{}]", code);
        /**
         * 什么要用 301 跳转而不是 302
         * 301 是永久重定向，302 是临时重定向。
         * 短地址一经生成就不会变化，所以用 301 是同时对服务器压力也会有一定减少
         * 但是如果使用了 301，无法统计到短地址被点击的次数。
         * 所以选择302虽然会增加服务器压力，但是有很多数据可以获取进行分析
         */
        // 判断短链码是否合法
        if (CheckUtil.isLetterOrDigit(code) && isValidDbAndTb(code)) {
            LinkDO shortLink = this.linkService.findOneByCode(code);
            if (shortLink != null) {
                this.logService.recordLog(request, code, shortLink.getAccountNo());
                response.setHeader("Location", CommonUtil.removeUrlPrefix(shortLink.getOriginalUrl()));
                // 302跳转
                response.setStatus(HttpStatus.FOUND.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        } else {
            throw new BizException(BizCodeEnum.SHORT_LINK_NOT_EXIST);
        }
    }

    private boolean isValidDbAndTb(String code) {
        if (code.length() < 3) {
            return false;
        }
        String db = String.valueOf(code.charAt(0));
        String tb = String.valueOf(code.charAt(code.length() - 1));
        boolean dbValid = false;
        boolean tbValid = false;
        for (Pair<String, Double> pair : ShardingConfig.dbNo) {
            if (pair.getFirst().equals(db)) {
                dbValid = true;
            }
        }
        for (Pair<String, Double> pair : ShardingConfig.tbNo) {
            if (pair.getFirst().equals(tb)) {
                tbValid = true;
            }
        }
        return tbValid && dbValid;
    }
}
