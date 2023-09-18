package com.linklyze.link.service.impl;

import com.linklyze.link.manager.DomainManager;
import com.linklyze.link.model.DomainDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author novo
 * @since 2023-03-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DomainServiceImplTest {

    @Autowired
    private DomainManager domainManager;

    @Test
    public void findAll() {
        List<DomainDO> officialAll = this.domainManager.findOfficialAll();
        log.info(officialAll.toString());
    }
}
