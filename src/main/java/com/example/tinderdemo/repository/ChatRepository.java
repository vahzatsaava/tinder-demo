package com.example.tinderdemo.repository;

import com.example.tinderdemo.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, String> {

    @Query("SELECT c FROM ChatEntity c WHERE c.fromUser.id = :userId OR c.toUser.id = :userId")
    List<ChatEntity> findAllByUser(@Param("userId") String userId);

    @Query("SELECT c from ChatEntity c WHERE c.id =:id")
    Optional<ChatEntity> findById(@Param("id") String id);

}
