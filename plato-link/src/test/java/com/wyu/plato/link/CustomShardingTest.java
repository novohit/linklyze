package com.wyu.plato.link;

import com.alibaba.nacos.shaded.io.opencensus.trace.Link;
import com.wyu.plato.link.mapper.ShortLinkMapper;
import com.wyu.plato.link.model.ShortLinkDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author novo
 * @since 2023-03-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CustomShardingTest {

    @Autowired
    private ShortLinkMapper shortLinkMapper;

    @Test
    public void createLink() {
        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setCode("0XXXXXXa");
        shortLinkDO.setLongHash("test");
        int rows = shortLinkMapper.insert(shortLinkDO);
    }
}
