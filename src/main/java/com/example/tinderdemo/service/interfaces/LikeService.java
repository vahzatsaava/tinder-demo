package com.example.tinderdemo.service.interfaces;

import com.example.tinderdemo.model.like.LikeDto;
import com.example.tinderdemo.model.like.LikeRequest;

import java.security.Principal;
import java.util.List;

public interface LikeService {
    LikeDto likeUser(Principal fromUser1, LikeRequest likesRequest);

    List<LikeDto> getLikesFromUser(Principal principal);

    List<LikeDto> getLikesToUser(Principal principal);
}
