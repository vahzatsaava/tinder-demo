package com.example.tinderdemo.controller;

import com.example.tinderdemo.model.UserDto;
import com.example.tinderdemo.model.UserUpdateDto;
import com.example.tinderdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получение аккаунта", description = "Получение своего аккаунта пользователем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аккаунт получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не получил аккаунт", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public UserDto getAccount(Principal principal){
        return userService.getAccount(principal);
    }

    @Operation(summary = "Удаление аккаунта", description = "Удаление аккаунта пользователем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удален аккаунт"),
            @ApiResponse(responseCode = "401", description = "Аккаунт не удалился", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping
    public void deleteAccount(Principal principal){
        userService.deleteAccount(principal);
    }

    @Operation(summary = "Обновление аккаунта пользователем", description = "Обновление своего аккаунта пользователем. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аккаунт обновлен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не получил аккаунт", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping
    public UserDto updateAccount(Principal principal,
                                 @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateAccount(principal,userUpdateDto);

    }
}
