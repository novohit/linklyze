package com.linklyze.account.service;

import com.linklyze.account.api.v1.request.LoginRequest;
import com.linklyze.account.api.v1.request.RegisterRequest;
import com.linklyze.account.model.AccountDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author novo
 * @since 2023-02-21
 */
public interface AccountService extends IService<AccountDO> {

    void register(RegisterRequest registerRequest);

    String login(LoginRequest loginRequest);

    AccountDO findByAccountNo(Long accountNo);
}
