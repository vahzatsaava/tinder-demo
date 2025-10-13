package com.example.tinderdemo.controller;

import com.example.tinderdemo.model.match.MatchDto;
import com.example.tinderdemo.service.interfaces.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @Operation(summary = "Получение match", description = "Получение взаимных match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match-и  получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не получил match-и", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<MatchDto> getMatches(Principal principal) {
        return matchService.matches(principal);
    }

    @Operation(summary = "Удаление совпадения матча", description = "Удаление пары.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удалена пара"),
            @ApiResponse(responseCode = "401", description = "Матч не удалился", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{emailToDelete}")
    public void deleteAccount(Principal principal, @PathVariable String emailToDelete) {
        matchService.deleteMatch(principal, emailToDelete);
    }
}
