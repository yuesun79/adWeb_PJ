package com.fudan.se.community.service.impl;

import com.fudan.se.community.service.AcceptService;
import com.fudan.se.community.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class AcceptServiceImplTest {
    @Autowired
    AcceptService acceptService;
    @Test
    @Transactional
    public void acceptTask_personal() {
        acceptService.acceptTask_personal(1, 3);
    }

}