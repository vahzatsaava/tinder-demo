package com.example.tinderdemo.service.interfaces;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.match.MatchDto;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface MatchService {
    MatchDto createMatch(User user1, User user2);

    List<MatchDto> matches(Principal principal);

    @Transactional
    void deleteMatch(Principal principal, String emailToDelete);

    Boolean existsMatchingBetweenUsers(String userIdFrom, String userIdTo);
}
