package com.linklyze.link;

import com.linklyze.link.strategy.ShardingConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author novo
 * @since 2023-03-16
 */
@Slf4j
public class ShortLinkTest {

    @Test
    public void createLinkDBPrefix() {
        for (int i = 0; i < 10; i++) {
            log.info("[db:{},table:{}]", ShardingConfig.getRandomDBNo(), ShardingConfig.getRandomTbNo());
        }
    }
}
