package com.fudan.se.community.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TaskMapperTest {
    @Autowired
    TaskMapper taskMapper;

    @Test
    void retrieveTasks_userId_inGroup() {
        Integer userId = 3;
        log.info(taskMapper.retrieveTasks_userId_inGroup(userId).toString());
    }

}