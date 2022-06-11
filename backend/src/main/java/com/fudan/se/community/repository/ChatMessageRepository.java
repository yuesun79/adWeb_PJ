package com.fudan.se.community.repository;

import com.fudan.se.community.pojo.message.ChatMessage;
import com.fudan.se.community.pojo.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {
    List<ChatMessage> findByTidAndAndRoomIdAndStatus(Integer tid, Integer roomId, boolean status);
    Page<ChatMessage> findAll(Pageable var);
}
