package com.wyu.plato.account.service.impl;

import com.wyu.plato.account.api.v1.request.RegisterRequest;
import com.wyu.plato.account.model.AccountDO;
import com.wyu.plato.account.mapper.AccountMapper;
import com.wyu.plato.account.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.plato.account.service.NotifyService;
import com.wyu.plato.common.enums.AccountAuthType;
import com.wyu.plato.common.enums.SendCodeType;
import com.wyu.plato.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // 1 校验验证码
        this.notifyService.verify(SendCodeType.USER_REGISTER_PHONE, registerRequest.getPhone(), registerRequest.getCode());
        // 2 构造入库对象
        AccountDO accountDO = new AccountDO();
        BeanUtils.copyProperties(registerRequest, accountDO);
        accountDO.setAuth(AccountAuthType.DEFAULT.name());
        accountDO.setSecret("$1$" + CommonUtil.getRandomCode(8));
        String cryptPassword = Md5Crypt.md5Crypt(registerRequest.getPassword().getBytes(), accountDO.getSecret());
        accountDO.setPassword(cryptPassword);
        // TODO 唯一账号怎么生成
        accountDO.setAccountNo(CommonUtil.getCurrentTimestamp());

        int row = this.accountMapper.insert(accountDO);
        log.info("row:[{}],注册成功:[{}]", row, accountDO);
        // 3 发放新用户福利 TODO
    }
}
