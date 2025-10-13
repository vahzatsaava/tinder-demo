package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.Like;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.entity.enums.LikeType;
import com.example.tinderdemo.mapper.LikeMapper;
import com.example.tinderdemo.model.like.LikeDto;
import com.example.tinderdemo.model.like.LikeRequest;
import com.example.tinderdemo.repository.LikeRepository;
import com.example.tinderdemo.service.interfaces.MatchService;
import com.example.tinderdemo.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserService userService;

    @Mock
    private MatchService matchService;

    @Mock
    private LikeMapper likeMapper;

    @InjectMocks
    private LikeServiceImpl likeService;


    @Test
    void likeUser_shouldReturnExistingLikeDto_ifAlreadyLiked() {
        Principal principal = () -> "user1@example.com";
        User fromUser = new User();
        fromUser.setId("u1");
        fromUser.setEmail(principal.getName());

        User toUser = new User();
        toUser.setId("u2");
        toUser.setEmail("user2@example.com");

        LikeRequest request = new LikeRequest();
        request.setToLikedUserEmail(toUser.getEmail());
        request.setLikeStatus(LikeType.LIKE);

        Like existingLike = new Like();
        existingLike.setId("l1");
        existingLike.setLikeStatus(LikeType.LIKE);
        existingLike.setFromUser(fromUser);
        existingLike.setToUser(toUser);

        LikeDto existingLikeDto = new LikeDto();
        existingLikeDto.setId("l1");

        when(userService.findUserByEmail(principal.getName())).thenReturn(fromUser);
        when(userService.findUserByEmail(toUser.getEmail())).thenReturn(toUser);
        when(likeRepository.existsByFromUserAndToUser(fromUser, toUser)).thenReturn(true);
        when(likeRepository.findByFromUserAndToUser(fromUser, toUser)).thenReturn(existingLike);
        when(likeMapper.toLikeDto(existingLike)).thenReturn(existingLikeDto);

        LikeDto result = likeService.likeUser(principal, request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("l1", result.getId());
        verify(likeRepository, never()).save(any());
        verify(matchService, never()).createMatch(any(), any());
    }

    @Test
    void likeUser_shouldCreateLikeAndMatch_forSuperlike() {
        Principal principal = () -> "user1@example.com";
        User fromUser = new User();
        fromUser.setId("u1");
        fromUser.setEmail(principal.getName());

        User toUser = new User();
        toUser.setId("u2");
        toUser.setEmail("user2@example.com");

        LikeRequest request = new LikeRequest();
        request.setToLikedUserEmail(toUser.getEmail());
        request.setLikeStatus(LikeType.SUPERLIKE);

        when(userService.findUserByEmail(principal.getName())).thenReturn(fromUser);
        when(userService.findUserByEmail(toUser.getEmail())).thenReturn(toUser);
        when(likeRepository.existsByFromUserAndToUser(fromUser, toUser)).thenReturn(false);

        LikeDto likeDto = new LikeDto();
        when(likeMapper.toLikeDto(any(Like.class))).thenReturn(likeDto);

        LikeDto result = likeService.likeUser(principal, request);

        Assertions.assertNotNull(result);
        verify(likeRepository).save(any(Like.class));
        verify(matchService).createMatch(fromUser, toUser);
    }

    @Test
    void likeUser_shouldCreateLikeAndMatch_forMutualLike() {
        Principal principal = () -> "user1@example.com";
        User fromUser = new User();
        fromUser.setId("u1");
        fromUser.setEmail(principal.getName());

        User toUser = new User();
        toUser.setId("u2");
        toUser.setEmail("user2@example.com");

        LikeRequest request = new LikeRequest();
        request.setToLikedUserEmail(toUser.getEmail());
        request.setLikeStatus(LikeType.LIKE);

        when(userService.findUserByEmail(principal.getName())).thenReturn(fromUser);
        when(userService.findUserByEmail(toUser.getEmail())).thenReturn(toUser);
        when(likeRepository.existsByFromUserAndToUser(fromUser, toUser)).thenReturn(false);
        when(likeRepository.existsByFromUserAndToUserAndLikeStatus(toUser, fromUser, LikeType.LIKE))
                .thenReturn(true);

        LikeDto likeDto = new LikeDto();
        when(likeMapper.toLikeDto(any(Like.class))).thenReturn(likeDto);

        LikeDto result = likeService.likeUser(principal, request);

        Assertions.assertNotNull(result);
        verify(likeRepository).save(any(Like.class));
        verify(matchService).createMatch(fromUser, toUser);
    }

    @Test
    void getLikesFromUser_shouldReturnListOfLikeDto() {
        Principal principal = () -> "user@example.com";

        Like like = new Like();
        List<Like> likes = List.of(like);

        when(likeRepository.findAllByFromUserEmailExcludingMatches(principal.getName())).thenReturn(likes);
        when(likeMapper.toLikeDtoList(likes)).thenReturn(List.of(new LikeDto()));

        List<LikeDto> result = likeService.getLikesFromUser(principal);

        Assertions.assertEquals(1, result.size());
        verify(likeRepository).findAllByFromUserEmailExcludingMatches(principal.getName());
        verify(likeMapper).toLikeDtoList(likes);
    }

    @Test
    void getLikesToUser_shouldReturnListOfLikeDto() {
        Principal principal = () -> "user@example.com";

        Like like = new Like();
        List<Like> likes = List.of(like);

        when(likeRepository.findAllByToUserEmailExcludingMatches(principal.getName())).thenReturn(likes);
        when(likeMapper.toLikeDtoList(likes)).thenReturn(List.of(new LikeDto()));

        List<LikeDto> result = likeService.getLikesToUser(principal);

        Assertions.assertEquals(1, result.size());
        verify(likeRepository).findAllByToUserEmailExcludingMatches(principal.getName());
        verify(likeMapper).toLikeDtoList(likes);
    }
}