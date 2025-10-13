package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.Like;
import com.example.tinderdemo.entity.enums.LikeType;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.mapper.LikeMapper;
import com.example.tinderdemo.model.like.LikeDto;
import com.example.tinderdemo.model.like.LikeRequest;
import com.example.tinderdemo.repository.LikeRepository;
import com.example.tinderdemo.service.interfaces.LikeService;
import com.example.tinderdemo.service.interfaces.MatchService;
import com.example.tinderdemo.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final MatchService matchService;
    private final LikeMapper likeMapper;


    @Override
    @Transactional
    public LikeDto likeUser(Principal fromUser1, LikeRequest likesRequest) {

        User fromUser = userService.findUserByEmail(fromUser1.getName());
        User toUser = userService.findUserByEmail(likesRequest.getToLikedUserEmail());
        log.info("fromUser id={}, email={}", fromUser.getId(), fromUser.getEmail());
        log.info("toUser id={}, email={}", toUser.getId(), toUser.getEmail());

        LikeDto existingLike = handleExistingLike(fromUser, toUser);
        if (existingLike != null) return existingLike;

        Like like = createAndSaveLike(fromUser, toUser, likesRequest.getLikeStatus());

        checkAndCreateMatch(like);

        return likeMapper.toLikeDto(like);
    }

    @Override
    public List<LikeDto> getLikesFromUser(Principal principal) {
        List<Like> likes = likeRepository
                .findAllByFromUserEmailExcludingMatches(principal.getName());

        return likeMapper.toLikeDtoList(likes);
    }

    @Override
    public List<LikeDto> getLikesToUser(Principal principal) {
        List<Like> likes = likeRepository
                .findAllByToUserEmailExcludingMatches(principal.getName());

        return likeMapper.toLikeDtoList(likes);
    }

    private LikeDto handleExistingLike(User from, User to) {
        if (likeRepository.existsByFromUserAndToUser(from, to)) {
            log.info("User {} already liked {}", from.getEmail(), to.getEmail());
            Like existingLike = likeRepository.findByFromUserAndToUser(from, to);
            log.info("Existing Like id={}, status={}", existingLike.getId(), existingLike.getLikeStatus());
            return likeMapper.toLikeDto(existingLike);
        }
        return null;
    }

    private Like createAndSaveLike(User fromUser, User toUser, LikeType type) {
        Like like = Like.builder()
                .id(UUID.randomUUID().toString())
                .fromUser(fromUser)
                .toUser(toUser)
                .likeStatus(type)
                .build();
        likeRepository.save(like);
        log.info("Saved Like id={}, status={}", like.getId(), like.getLikeStatus());
        return like;
    }

    private void checkAndCreateMatch(Like like) {
        if (like.getLikeStatus() == LikeType.SUPERLIKE) {
            log.info("SUPERLIKE detected, creating match...");
            matchService.createMatch(like.getFromUser(), like.getToUser());
        } else if (like.getLikeStatus() == LikeType.LIKE &&
                likeRepository.existsByFromUserAndToUserAndLikeStatus(
                        like.getToUser(), like.getFromUser(), LikeType.LIKE)) {
            log.info("Mutual LIKE detected, creating match...");
            matchService.createMatch(like.getFromUser(), like.getToUser());
        }
    }


}
