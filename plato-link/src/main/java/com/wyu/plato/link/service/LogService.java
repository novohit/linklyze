package com.wyu.plato.link.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author novo
 * @since 2023-03-27
 */
public interface LogService {

    void recordLog(HttpServletRequest request, String code);
}
