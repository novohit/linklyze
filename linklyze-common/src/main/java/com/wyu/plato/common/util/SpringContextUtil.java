package com.wyu.plato.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @date 2023-01-09 16:23
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据Bean名称获取一个Bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据Bean类型获取一个Bean
     * @param requiredType
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据Bean名称和类型获取一个Bean
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 容器中是否存在
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 是否单例
     * @param name
     * @return
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }
}
