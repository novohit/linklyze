package com.linklyze.account;

import com.linklyze.account.mapper.AccountMapper;
import com.linklyze.account.model.AccountDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author novo
 * @since 2023-03-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testSaveAccount() {
        AccountDO accountDO = new AccountDO();
        accountDO.setUsername("test");
        accountMapper.insert(accountDO);
    }
}
