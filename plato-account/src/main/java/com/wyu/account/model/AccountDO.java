package com.wyu.account.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wyu.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("account")
public class AccountDO extends BaseModel {


    @TableId(value = "id", type = IdType.AUTO)
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
     * 密码
     */
    private String password;

    /**
     * 盐，用于个人敏感信息处理
     */
    private String secret;

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


}
