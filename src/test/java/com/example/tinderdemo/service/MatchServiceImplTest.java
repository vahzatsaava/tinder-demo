package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.Match;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.entity.enums.MatchStatus;
import com.example.tinderdemo.mapper.MatchMapper;
import com.example.tinderdemo.model.match.MatchDto;
import com.example.tinderdemo.repository.MatchRepository;
import com.example.tinderdemo.service.interfaces.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchMapper matchMapper;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private MatchServiceImpl matchService;


    @Test
    void createMatch_shouldReturnMatchDto_whenNotAlreadyMatched() {
        User user1 = new User();
        user1.setId("u1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId("u2");
        user2.setEmail("user2@example.com");

        when(matchRepository.existsByUsers(user1.getId(), user2.getId())).thenReturn(false);

        MatchDto matchDto = new MatchDto();
        matchDto.setId("m1");

        when(matchMapper.toMatchDto(any(Match.class))).thenReturn(matchDto);

        MatchDto result = matchService.createMatch(user1, user2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("m1", result.getId());

        verify(matchRepository).save(any(Match.class));
        verify(chatService).createChat(user1, user2);
        verify(matchMapper).toMatchDto(any(Match.class));
    }

    @Test
    void createMatch_shouldReturnNull_whenAlreadyMatched() {
        User user1 = new User();
        user1.setId("u1");

        User user2 = new User();
        user2.setId("u2");

        when(matchRepository.existsByUsers(user1.getId(), user2.getId())).thenReturn(true);

        MatchDto result = matchService.createMatch(user1, user2);

        assertNull(result);
        verify(matchRepository, never()).save(any());
        verify(chatService, never()).createChat(any(), any());
    }

    @Test
    void matches_shouldReturnListOfMatchDto() {
        Principal principal = () -> "user@example.com";

        Match match = new Match();
        List<Match> matches = List.of(match);

        when(matchRepository.findMatchesByUserEmail(principal.getName())).thenReturn(matches);
        when(matchMapper.toMatchDtoList(matches)).thenReturn(List.of(new MatchDto()));

        List<MatchDto> result = matchService.matches(principal);

        Assertions.assertEquals(1, result.size());
        verify(matchRepository).findMatchesByUserEmail(principal.getName());
        verify(matchMapper).toMatchDtoList(matches);
    }

    @Test
    void deleteMatch_shouldSetMatchStatusToDelete_whenMatchExists() {
        Principal principal = () -> "user1@example.com";
        String emailToDelete = "user2@example.com";

        Match match = new Match();
        when(matchRepository.findMatchBetweenUsers(principal.getName(), emailToDelete))
                .thenReturn(Optional.of(match));

        matchService.deleteMatch(principal, emailToDelete);

        Assertions.assertEquals(MatchStatus.DELETE, match.getMatchStatus());
    }

    @Test
    void deleteMatch_shouldThrowException_whenMatchNotFound() {
        Principal principal = () -> "user1@example.com";
        String emailToDelete = "user2@example.com";

        when(matchRepository.findMatchBetweenUsers(principal.getName(), emailToDelete))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> matchService.deleteMatch(principal, emailToDelete));
    }

    @Test
    void existsMatchingBetweenUsers_shouldReturnTrue() {
        when(matchRepository.existsByUsers("u1", "u2")).thenReturn(true);

        boolean result = matchService.existsMatchingBetweenUsers("u1", "u2");

        Assertions.assertTrue(result);
        verify(matchRepository).existsByUsers("u1", "u2");
    }

}