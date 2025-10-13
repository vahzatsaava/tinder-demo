package com.example.tinderdemo.controller;

import com.example.tinderdemo.model.chat.ChatDto;
import com.example.tinderdemo.model.chat.MessageDto;
import com.example.tinderdemo.model.chat.MessageRequestDto;
import com.example.tinderdemo.service.ChatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    @Operation(summary = "Получить список чатов пользователя",
            description = "Возвращает все чаты, в которых участвует текущий пользователь.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Чаты успешно получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<ChatDto> getUserChats(Principal principal) {
        return chatService.getUserChats(principal);
    }

    @Operation(summary = "Получить сообщения чата",
            description = "Возвращает все сообщения по указанному ID чата.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщения успешно получены"),
            @ApiResponse(responseCode = "404", description = "Чат не найден")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{chatId}/messages")
    public List<MessageDto> getMessages(@PathVariable String chatId) {
        return chatService.getMessagesByChatId(chatId);
    }

    @Operation(summary = "Отправить сообщение",
            description = "Создает новое сообщение в выбранном чате.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно отправлено"),
            @ApiResponse(responseCode = "404", description = "Чат не найден")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/messages")
    public MessageDto sendMessage(Principal principal, @RequestBody MessageRequestDto messageRequestDto) {
        return chatService.createMessage(principal, messageRequestDto);
    }

    @Operation(summary = "Получить последнее сообщение в чате",
            description = "Возвращает последнее сообщение для конкретного чата.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно получено"),
            @ApiResponse(responseCode = "404", description = "Чат не найден")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{chatId}/last-message")
    public MessageDto getLastMessage(@PathVariable String chatId) {
        return chatService.getLastMessageForChat(chatId);
    }

}