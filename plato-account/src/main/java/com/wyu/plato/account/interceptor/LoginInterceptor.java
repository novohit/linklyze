package com.wyu.plato.account.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.plato.account.model.AccountDO;
import com.wyu.plato.account.service.AccountService;
import com.wyu.plato.common.LocalUserThreadHolder;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.LocalUser;
import com.wyu.plato.common.util.TokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author novo
 * @since 2023-03-07
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER = "Bearer";

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor...");
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        // token为空
        if (!StringUtils.hasText(authorization)) {
            log.info("token为空");
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN, HttpStatus.UNAUTHORIZED);
        }
        // token格式不正确
        if (!authorization.startsWith(BEARER)) {
            log.info("token格式错误");
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN, HttpStatus.UNAUTHORIZED);
        }
        String[] tokens = authorization.split(" ");
        // 避免数组越界
        if (tokens.length != 2) {
            log.info("token格式错误");
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN, HttpStatus.UNAUTHORIZED);
        }
        // 校验
        Claims claims = TokenUtil.verifyToken(tokens[1]);
        if (claims == null) {
            log.info("token不合法");
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN, HttpStatus.UNAUTHORIZED);
        }
        String accountNo = (String) claims.get("account_no");
        AccountDO dbAccount = this.accountService.getBaseMapper().selectOne(new QueryWrapper<AccountDO>().lambda().eq(AccountDO::getAccountNo, accountNo));
        LocalUser localUser = new LocalUser();
        BeanUtils.copyProperties(dbAccount, localUser);
        // TODO 用户等级
        localUser.setScope(1);
        /**
         * 用户信息传递：
         * 方式一：Request Attribute 传递
         * 方式二：ThreadLocal 传递
         */
        LocalUserThreadHolder.setLocalUser(localUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO 记得释放资源，避免内存泄露
        LocalUserThreadHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
