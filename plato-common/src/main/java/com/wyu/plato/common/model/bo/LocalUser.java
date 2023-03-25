package com.wyu.plato.common.model.bo;


import lombok.Data;

/**
 * @author zwx
 * @date 2022-07-13 16:46
 */
@Data
public class LocalUser {

    private Long id;

    private Long accountNo;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户名
     */
    private String username;

    /**
     * 认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样
     */
    private String auth;

    /**
     * TODO 用户权限等级
     */
    private Integer scope;
}
