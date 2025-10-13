package com.example.tinderdemo.repository;

import com.example.tinderdemo.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Match m " +
            "WHERE (m.user1.id = :id1 AND m.user2.id = :id2) " +
            "   OR (m.user1.id = :id2 AND m.user2.id = :id1)")
    boolean existsByUsers(@Param("id1") String id1, @Param("id2") String id2);

    @Query("SELECT m FROM Match m WHERE m.user1.email = :email OR m.user2.email = :email")
    List<Match> findMatchesByUserEmail(@Param("email") String email);

    @Query("""
                SELECT m FROM Match m 
                WHERE (m.user1.email = :email1 AND m.user2.email = :email2)
                   OR (m.user1.email = :email2 AND m.user2.email = :email1)
            """)
    Optional<Match> findMatchBetweenUsers(@Param("email1") String email1, @Param("email2") String email2);


}
