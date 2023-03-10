package com.wyu.plato.account;

import com.wyu.plato.account.mapper.TrafficMapper;
import com.wyu.plato.account.model.TrafficDO;
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

    @Test
    public void testSaveTraffic() {
        for (int i = 0; i < 5; i++) {
            TrafficDO trafficDO = new TrafficDO();
            trafficDO.setAccountNo(CommonUtil.getCurrentTimestamp());
            trafficMapper.insert(trafficDO);
        }
    }
}
