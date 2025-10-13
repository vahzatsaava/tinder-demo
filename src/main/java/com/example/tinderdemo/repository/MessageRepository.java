package com.example.tinderdemo.repository;

import com.example.tinderdemo.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findByChatIdOrderBySentAtAsc(String chatId);
    Optional<MessageEntity> findTopByChatIdOrderBySentAtDesc(String chatId);

}

