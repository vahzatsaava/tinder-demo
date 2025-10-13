package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.Match;
import com.example.tinderdemo.entity.enums.MatchStatus;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.mapper.MatchMapper;
import com.example.tinderdemo.model.match.MatchDto;
import com.example.tinderdemo.repository.MatchRepository;
import com.example.tinderdemo.service.interfaces.ChatService;
import com.example.tinderdemo.service.interfaces.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper mapper;
    private final ChatService chatService;

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "matches", key = "#user1.email"),
            @CacheEvict(value = "matches", key = "#user2.email")
    })
    public MatchDto createMatch(User user1, User user2) {
        boolean alreadyMatched = existsMatchingBetweenUsers(user1.getId(), user2.getId());

        if (alreadyMatched) return null;

        Match match = Match.builder()
                .id(UUID.randomUUID().toString())
                .user1(user1)
                .user2(user2)
                .matchedAt(LocalDateTime.now())
                .matchStatus(MatchStatus.ACTIVE)
                .build();

        matchRepository.save(match);
        log.info("Creating match between {} and {}", user1.getEmail(), user2.getEmail());

        chatService.createChat(user1, user2);
        log.info("Chat created between {} and {}", user1.getEmail(), user2.getEmail());

        return mapper.toMatchDto(match);
    }

    @Override
    @Cacheable(value = "matches", key = "#principal.name")
    public List<MatchDto> matches(Principal principal) {
        List<Match> matches = matchRepository.findMatchesByUserEmail(principal.getName());
        return mapper.toMatchDtoList(matches);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "matches", key = "#principal.name"),
            @CacheEvict(value = "matches", key = "#emailToDelete")
    })
    public void deleteMatch(Principal principal, String emailToDelete) {
        String currentUserEmail = principal.getName();

        Optional<Match> matchOpt = matchRepository.findMatchBetweenUsers(currentUserEmail, emailToDelete);

        if (matchOpt.isEmpty()) {
            log.warn("No match found between {} and {}", currentUserEmail, emailToDelete);
            throw new IllegalArgumentException("Матч не найден или уже удалён");
        }

        Match match = matchOpt.get();
        match.setMatchStatus(MatchStatus.DELETE);
        log.info("Match deleted between {} and {}", currentUserEmail, emailToDelete);
    }

    @Override
    public Boolean existsMatchingBetweenUsers(String userIdFrom, String userIdTo) {
        return matchRepository.existsByUsers(userIdFrom, userIdTo);
    }
}
