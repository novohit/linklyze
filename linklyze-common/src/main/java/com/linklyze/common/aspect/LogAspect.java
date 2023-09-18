package com.linklyze.common.aspect;

import com.google.gson.Gson;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 开发环境日志
 *
 * @author novo
 * @since 2023-02-26 22:19
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Gson gson = new Gson();

    /**
     * execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
     * 第一个'*'符号，表示返回值类型任意；
     * com.linklyze，AOP所切的服务的包名，即我们的业务部分
     * api包名后面的'..'，表示当前包及子包
     * 第二个'*'，表示类名，*即所有类
     * .*(..)，表示任何方法名，括号表示参数，两个点表示任何参数类型
     */
    @Pointcut("execution(public * com.linklyze.*.api..*.*(..))")
    public void webLog() {
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (logger.isDebugEnabled()) {
            // 打印请求相关参数
            logger.debug("========================================== Start ==========================================");
            // 打印请求 url
            logger.debug("URL            : {}", request.getRequestURL().toString());
            // 打印 Http method
            logger.debug("HTTP Method    : {}", request.getMethod());
            // 打印调用 controller 的全路径以及执行方法
            logger.debug("Class Method   : {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
            // 打印请求的 IP
            logger.debug("IP             : {}", request.getRemoteAddr());
            // 经过nginx代理后的请求头
            logger.debug("Real-IP        : {}", request.getHeader("X-Real-IP"));
            logger.debug("X-Forwarded-For: {}", request.getHeader("X-Forwarded-For"));
            // 打印请求入参
            // 注意如果controller方法参数上含有request/response对象 不能进行序列化 因为这两个对象中含有循环依赖 会导致oom问题
            Object[] args = proceedingJoinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                // 排除这两个对象
                if (args[i] instanceof ResponseFacade || args[i] instanceof RequestFacade) {
                    args[i] = "request/response";
                }
            }
            //logger.debug("Request Args   : {}", JSONArray.toJSONString(args));
            //logger.debug("Request Args   : \n{}", gson.toJson(args));
            // 这里用Gson序列化 其他解析不了文件类型
            logger.debug("Request Args   : {}", gson.toJson(args));
        }

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();

        if (logger.isDebugEnabled()) {
            // 打印出参
            logger.debug("Response Args  : {}", gson.toJson(result));
            //logger.debug("Response Args  : \n{}", gson.toJson(result));
            //logger.debug("Response Args  : {}", JSONObject.toJSONString(result));
            // 执行耗时
            logger.debug("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
            logger.debug("=========================================== End ===========================================");
            // 每个请求之间空一行
            logger.debug("");
        }

        return result;
    }

    @AfterThrowing("webLog()")
    public void doAfterThrowing() {
        if (logger.isDebugEnabled()) {
            logger.debug("=========================================== End ===========================================");
            // 每个请求之间空一行
            logger.debug("");
        }
    }
}
