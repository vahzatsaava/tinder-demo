package com.example.tinderdemo.controller;

import com.example.tinderdemo.model.like.LikeDto;
import com.example.tinderdemo.model.like.LikeRequest;
import com.example.tinderdemo.service.interfaces.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;


    @Operation(summary = "Пользователь ставит лайк другому пользователю", description = "Пользователь ставит лайк. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайк выполнен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не сделал лайк", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public LikeDto likeUser(Principal principal, @Valid
    @RequestBody LikeRequest likeRequest) {
        return likeService.likeUser(principal, likeRequest);

    }

    @Operation(summary = "Получение лайков который сделал пользователь", description = "Получить лайки сделанные пользователем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайки получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не получил Лайки", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/from")
    public List<LikeDto> getLikesFromUser(Principal principal) {
        return likeService.getLikesFromUser(principal);
    }

    @Operation(summary = "Получение лайков которые поставили пользователю", description = "Получить лайки которые сделали пользователю.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайки получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не получил Лайки", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/to")
    public List<LikeDto> getLikesToUser(Principal principal) {
        return likeService.getLikesToUser(principal);
    }
}
