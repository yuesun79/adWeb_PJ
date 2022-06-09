package com.fudan.se.community.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomServiceImplTest {
    @Autowired
    RoomServiceImpl roomService;

    @Test
    void findUsersInRoom() {
        Integer roomId = 1;
        log.info(roomService.findUsersInRoom(roomId).toString());
    }
}