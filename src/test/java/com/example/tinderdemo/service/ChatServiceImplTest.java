package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.ChatEntity;
import com.example.tinderdemo.entity.MessageEntity;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.exceptions.ChatAccessDeniedException;
import com.example.tinderdemo.exceptions.MessageNotFoundException;
import com.example.tinderdemo.mapper.ChatMapper;
import com.example.tinderdemo.mapper.MessageMapper;
import com.example.tinderdemo.model.chat.ChatDto;
import com.example.tinderdemo.model.chat.MessageDto;
import com.example.tinderdemo.model.chat.MessageRequestDto;
import com.example.tinderdemo.repository.ChatRepository;
import com.example.tinderdemo.repository.MessageRepository;
import com.example.tinderdemo.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @Mock
    private ChatMapper chatMapper;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private ChatServiceImpl chatService;


    @Test
    void createChat_shouldReturnChatDto() {
        User fromUser = new User();
        fromUser.setEmail("from@example.com");
        User toUser = new User();
        toUser.setEmail("to@example.com");

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId("chat1");
        chatEntity.setFromUser(fromUser);
        chatEntity.setToUser(toUser);
        chatEntity.setCreatedAt(LocalDateTime.now());

        ChatDto chatDto = new ChatDto();
        chatDto.setId("chat1");

        when(chatRepository.save(any(ChatEntity.class))).thenReturn(chatEntity);
        when(chatMapper.toChatDto(chatEntity)).thenReturn(chatDto);

        ChatDto result = chatService.createChat(fromUser, toUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("chat1", result.getId());
        verify(chatRepository).save(any(ChatEntity.class));
        verify(chatMapper).toChatDto(chatEntity);
    }

    @Test
    void getUserChats_shouldReturnListOfChatDto() {
        Principal principal = () -> "user@example.com";
        User currentUser = new User();
        currentUser.setId("u1");
        currentUser.setEmail(principal.getName());

        ChatEntity chat = new ChatEntity();
        List<ChatEntity> chats = List.of(chat);

        ChatDto chatDto = new ChatDto();

        when(userService.findUserByEmail(principal.getName())).thenReturn(currentUser);
        when(chatRepository.findAllByUser(currentUser.getId())).thenReturn(chats);
        when(chatMapper.toChatDtos(chats)).thenReturn(List.of(chatDto));

        List<ChatDto> result = chatService.getUserChats(principal);

        Assertions.assertEquals(1, result.size());
        verify(chatRepository).findAllByUser(currentUser.getId());
        verify(chatMapper).toChatDtos(chats);
    }

    @Test
    void getMessagesByChatId_shouldReturnListOfMessageDto() {
        String chatId = "chat1";
        MessageEntity message = new MessageEntity();
        List<MessageEntity> messages = List.of(message);

        MessageDto messageDto = new MessageDto();

        when(messageRepository.findByChatIdOrderBySentAtAsc(chatId)).thenReturn(messages);
        when(messageMapper.toMessageDtos(messages)).thenReturn(List.of(messageDto));

        List<MessageDto> result = chatService.getMessagesByChatId(chatId);

        Assertions.assertEquals(1, result.size());
        verify(messageRepository).findByChatIdOrderBySentAtAsc(chatId);
        verify(messageMapper).toMessageDtos(messages);
    }

    @Test
    void getLastMessageForChat_shouldReturnMessageDto() {
        String chatId = "chat1";
        MessageEntity message = new MessageEntity();
        MessageDto messageDto = new MessageDto();

        when(messageRepository.findTopByChatIdOrderBySentAtDesc(chatId))
                .thenReturn(Optional.of(message));
        when(messageMapper.toMessageDto(message)).thenReturn(messageDto);

        MessageDto result = chatService.getLastMessageForChat(chatId);

        Assertions.assertNotNull(result);
        verify(messageRepository).findTopByChatIdOrderBySentAtDesc(chatId);
        verify(messageMapper).toMessageDto(message);
    }

    @Test
    void getLastMessageForChat_shouldThrowException_ifNoMessage() {
        String chatId = "chat1";

        when(messageRepository.findTopByChatIdOrderBySentAtDesc(chatId))
                .thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> chatService.getLastMessageForChat(chatId));
    }

    @Test
    void createMessage_shouldSaveAndReturnMessageDto() {
        Principal principal = () -> "user1@example.com";

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId("chat1");
        User fromUser = new User();
        fromUser.setEmail(principal.getName());
        chatEntity.setFromUser(fromUser);
        chatEntity.setToUser(new User());

        MessageRequestDto requestDto = new MessageRequestDto();
        requestDto.setChatId("chat1");
        requestDto.setContent("Hello!");

        when(chatRepository.findById("chat1")).thenReturn(Optional.of(chatEntity));
        when(userService.findUserByEmail(principal.getName())).thenReturn(fromUser);

        MessageEntity savedMessage = new MessageEntity();
        savedMessage.setId("msg1");

        MessageDto messageDto = new MessageDto();
        messageDto.setId("msg1");

        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessage);
        when(messageMapper.toMessageDto(savedMessage)).thenReturn(messageDto);

        MessageDto result = chatService.createMessage(principal, requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("msg1", result.getId());
        verify(messageRepository).save(any(MessageEntity.class));
    }

}