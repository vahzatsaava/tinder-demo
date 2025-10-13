package com.example.tinderdemo.service.interfaces;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.chat.ChatDto;
import com.example.tinderdemo.model.chat.MessageDto;
import com.example.tinderdemo.model.chat.MessageRequestDto;

import java.security.Principal;
import java.util.List;

public interface ChatService {
    ChatDto createChat(User emailFrom, User emailTo);

    List<ChatDto> getUserChats(Principal principal);

    List<MessageDto> getMessagesByChatId(String chatId);

    MessageDto getLastMessageForChat(String chatId);

    MessageDto createMessage(Principal principal,
                             MessageRequestDto messageRequestDto);
}
