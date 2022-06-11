package com.fudan.se.community.repository;

import com.fudan.se.community.pojo.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findBySid(Integer sid);
    Page<Message> findAll(Pageable var);
}
