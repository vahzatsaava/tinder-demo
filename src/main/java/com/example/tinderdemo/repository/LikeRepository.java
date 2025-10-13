package com.example.tinderdemo.repository;

import com.example.tinderdemo.entity.Like;
import com.example.tinderdemo.entity.enums.LikeType;
import com.example.tinderdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {

    Like findByFromUserAndToUser(User fromUser, User toUser);
    Boolean existsByFromUserAndToUser(User fromUser, User toUser);

    @Query("""
    SELECT l FROM Like l
    WHERE l.fromUser.email = :email
      AND NOT EXISTS (
          SELECT m FROM Match m
          WHERE (m.user1 = l.fromUser AND m.user2 = l.toUser)
             OR (m.user1 = l.toUser AND m.user2 = l.fromUser)
      )
    """)
    List<Like> findAllByFromUserEmailExcludingMatches(@Param("email") String email);


    @Query("""
    SELECT l FROM Like l
    WHERE l.toUser.email = :email
      AND NOT EXISTS (
          SELECT m FROM Match m
          WHERE (m.user1 = l.fromUser AND m.user2 = l.toUser)
             OR (m.user1 = l.toUser AND m.user2 = l.fromUser)
      )
    """)
    List<Like> findAllByToUserEmailExcludingMatches(@Param("email") String email);


    boolean existsByFromUserAndToUserAndLikeStatus(User fromUser, User toUser, LikeType likeStatus);
}
