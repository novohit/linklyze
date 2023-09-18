package com.linklyze.account.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linklyze.account.service.AccountService;
import com.linklyze.account.service.NotifyService;
import com.linklyze.account.api.v1.request.LoginRequest;
import com.linklyze.account.api.v1.request.RegisterRequest;
import com.linklyze.account.model.AccountDO;
import com.linklyze.account.mapper.AccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.common.enums.AccountAuthType;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.SendCodeType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.bo.LocalUser;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.common.util.TokenUtil;
import com.wyu.plato.common.util.uuid.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author novo
 * @since 2023-02-21
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDO> implements AccountService {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 单库下可在数据库层面用唯一索引保证手机号唯一
     * TODO 分库分表下如何保证？
     * 手机号不能作为一个账户的唯一标识，因为手机号有可能会变动，
     * 不能用手机号去绑定其他的业务，而是用一个固定且唯一account_no编号
     *
     * @param registerRequest
     */
    @Override
    public void register(RegisterRequest registerRequest) {
        String phone = registerRequest.getPhone();
        // 1 校验验证码
        boolean verify = this.notifyService.verify(SendCodeType.USER_REGISTER_PHONE, phone, registerRequest.getCode());
        if (!verify) {
            throw new BizException(BizCodeEnum.CODE_ERROR);
        }
        log.info("注册验证码校验成功");

        List<AccountDO> accounts = this.accountMapper
                .selectList(new QueryWrapper<AccountDO>().lambda().eq(AccountDO::getPhone, phone));
        if (!CollectionUtils.isEmpty(accounts)) {
            log.info("账户已存在,[{}]", phone);
            throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);
        }
        // 2 构造入库对象
        AccountDO accountDO = new AccountDO();
        BeanUtils.copyProperties(registerRequest, accountDO);
        accountDO.setAuth(AccountAuthType.DEFAULT.name());
        accountDO.setSecret("$1$" + CommonUtil.getRandomCode(8));
        String cryptPassword = Md5Crypt.md5Crypt(registerRequest.getPassword().getBytes(), accountDO.getSecret());
        accountDO.setPassword(cryptPassword);
        // 使用雪花算法生成唯一账号
        accountDO.setAccountNo(IDUtil.snowflakeID());

        int row = this.accountMapper.insert(accountDO);
        log.info("row:[{}],注册成功:[{}]", row, accountDO);
        // 3 发放新用户福利 TODO
    }

    @Override
    public String login(LoginRequest loginRequest) {
        // 1 根据手机号查询db
        String phone = loginRequest.getPhone();
        List<AccountDO> accounts = this.accountMapper
                .selectList(new QueryWrapper<AccountDO>().lambda().eq(AccountDO::getPhone, phone));
        if (accounts.size() != 1) {
            if (accounts.size() > 1) {
                log.error("同一手机存在多个账号 phone:[{}], accounts:[{}]", phone, accounts);
            }
            log.info("账户不存在,[{}]", phone);
            throw new BizException(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }
        AccountDO dbAccount = accounts.get(0);
        log.info("账户:[{}]", dbAccount);
        // 2 核对密码
        String cryptPassword = Md5Crypt.md5Crypt(loginRequest.getPassword().getBytes(), dbAccount.getSecret());
        if (!cryptPassword.equals(dbAccount.getPassword())) {
            throw new BizException(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }
        LocalUser localUser = new LocalUser();
        BeanUtils.copyProperties(dbAccount, localUser);
        HashMap account = JSONObject.parseObject(JSONObject.toJSONString(localUser), HashMap.class);

        // 3 生成token返回
        return TokenUtil.generateAccessToken(account);
    }

    @Override
    public AccountDO findByAccountNo(Long accountNo) {
        return this.accountMapper.selectOne(new QueryWrapper<AccountDO>().lambda().eq(AccountDO::getAccountNo, accountNo));
    }
}
