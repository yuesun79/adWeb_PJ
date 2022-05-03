package com.fudan.se.community.service.impl;

import com.fudan.se.community.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    TaskService taskService;
    @Test
    void findTask_id() {
        Integer id = 2;
        log.info(taskService.findTask_id(2).toString());
    }

}