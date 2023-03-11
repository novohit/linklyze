package com.wyu.link;

import com.google.common.hash.Hashing;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.link.component.ShortLinkComponent;
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
public class MurmurHashTest {

    @Autowired
    private ShortLinkComponent shortLinkComponent;

    @Test
    public void testMurmurHash() {
        for (int i = 0; i < 10; i++) {
            String originalUrl = "https://www.baidu.com?id="+ CommonUtil.getCurrentTimestamp() + "&name="+CommonUtil.getCurrentTimestamp();
            long murmur3_32 = Hashing.murmur3_32().hashUnencodedChars(originalUrl).padToLong();
            log.info("[{}]",murmur3_32);
        }
    }

    @Test
    public void testCreateShortLink() {
        this.shortLinkComponent.encodeToBase62(201314L);
        this.shortLinkComponent.encodeToBase622(201314L);
    }
}
