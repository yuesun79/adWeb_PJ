package com.fudan.se.community.repository;

import com.fudan.se.community.pojo.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageRepositoryTest {
    @Autowired
    MessageRepository messageRepository;

    Message message = new Message(1, "with ID ?");

    @Test
    public void saveMessage() {
        messageRepository.save(message);
        log.info(String.valueOf(message));
    }

    @Test
    public void findMessageBySidAndRoomId() {
        log.info(messageRepository.findBySid(1).toString());
    }

    @Test
    public void deleteMessage() {
        message.setId("62a32d42f18f802d069c02e1");
        messageRepository.delete(message);
    }

}