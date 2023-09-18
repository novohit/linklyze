package com.linklyze.link;

import com.linklyze.link.mapper.LinkMapper;
import com.linklyze.link.model.LinkDO;
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
    private LinkMapper linkMapper;

    @Test
    public void createLink() {
        LinkDO linkDO = new LinkDO();
        linkDO.setCode("0XXXXXXa");
        linkDO.setLongHash("test");
        int rows = linkMapper.insert(linkDO);
    }
}
