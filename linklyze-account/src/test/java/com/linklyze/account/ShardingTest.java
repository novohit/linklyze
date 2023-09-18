package com.linklyze.account;

import com.linklyze.account.mapper.AccountMapper;
import com.linklyze.account.mapper.TrafficMapper;
import com.linklyze.account.model.AccountDO;
import com.linklyze.account.model.TrafficDO;
import com.wyu.plato.common.util.CommonUtil;
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
    private TrafficMapper trafficMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testSaveTraffic() {
        for (int i = 0; i < 10; i++) {
            TrafficDO trafficDO = new TrafficDO();
            trafficDO.setAccountNo(CommonUtil.getCurrentTimestamp());
            trafficDO.setLevel("test");
            trafficDO.setDayLimit(10);
            trafficMapper.insert(trafficDO);
        }
    }

    @Test
    public void testSaveAccount() {
        AccountDO accountDO = new AccountDO();
        accountDO.setUsername("test");
        accountMapper.insert(accountDO);
    }
}
