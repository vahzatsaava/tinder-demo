package com.example.tinderdemo.service;

import com.example.tinderdemo.entity.ChatEntity;
import com.example.tinderdemo.entity.MessageEntity;
import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.exceptions.ChatAccessDeniedException;
import com.example.tinderdemo.exceptions.ChatNotFoundException;
import com.example.tinderdemo.exceptions.MessageNotFoundException;
import com.example.tinderdemo.mapper.ChatMapper;
import com.example.tinderdemo.mapper.MessageMapper;
import com.example.tinderdemo.model.chat.ChatDto;
import com.example.tinderdemo.model.chat.MessageDto;
import com.example.tinderdemo.model.chat.MessageRequestDto;
import com.example.tinderdemo.repository.ChatRepository;
import com.example.tinderdemo.repository.MessageRepository;
import com.example.tinderdemo.service.interfaces.ChatService;
import com.example.tinderdemo.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userChats", key = "#emailFrom.email"),
            @CacheEvict(value = "userChats", key = "#emailTo.email")
    })
    public ChatDto createChat(User emailFrom, User emailTo) {
        log.info("Creating chat between {} and {}", emailFrom.getEmail(), emailTo.getEmail());

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(UUID.randomUUID().toString());
        chatEntity.setFromUser(emailFrom);
        chatEntity.setToUser(emailTo);
        chatEntity.setCreatedAt(LocalDateTime.now());

        ChatEntity savedChat = chatRepository.save(chatEntity);
        log.debug("Chat created with id={}", savedChat.getId());

        return chatMapper.toChatDto(savedChat);
    }

    @Override
    @Cacheable(value = "userChats", key = "#principal.name")
    public List<ChatDto> getUserChats(Principal principal) {
        User currentUser = userService.findUserByEmail(principal.getName());
        log.info("Fetching chats for user: {}", principal.getName());
        List<ChatEntity> chats = chatRepository.findAllByUser(currentUser.getId());
        log.debug("Found {} chats for user {}", chats.size(), principal.getName());
        return chatMapper.toChatDtos(chats);
    }

    @Override
    public List<MessageDto> getMessagesByChatId(String chatId) {
        log.info("Getting messages for chatId={}", chatId);
        List<MessageEntity> messages = messageRepository.findByChatIdOrderBySentAtAsc(chatId);
        log.debug("Found {} messages for chatId={}", messages.size(), chatId);
        return messageMapper.toMessageDtos(messages);
    }

    @Override
    public MessageDto getLastMessageForChat(String chatId) {
        log.info("Getting last message for chatId={}", chatId);

        return messageRepository.findTopByChatIdOrderBySentAtDesc(chatId)
                .map(messageMapper::toMessageDto)
                .orElseThrow(() -> new MessageNotFoundException("No messages found for chatId=" + chatId));
    }

    @Override
    @Transactional
    public MessageDto createMessage(Principal principal,
                                    MessageRequestDto messageRequestDto) {
        ChatEntity chatEntity = chatRepository.findById(messageRequestDto.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat id not found by id " + messageRequestDto.getChatId()));
        User userSender = userService.findUserByEmail(principal.getName());
        checkChatAccess(chatEntity, principal);

        MessageEntity message = MessageEntity.builder()
                .id(UUID.randomUUID().toString())
                .content(messageRequestDto.getContent())
                .chat(chatEntity)
                .sentAt(LocalDateTime.now())
                .sender(userSender)
                .build();
        return messageMapper.toMessageDto(messageRepository.save(message));
    }

    private void checkChatAccess(ChatEntity chatEntity, Principal principal) {
        if (!chatEntity.getFromUser().getEmail().equals(principal.getName()) &&
                !chatEntity.getToUser().getEmail().equals(principal.getName())) {
            throw new ChatAccessDeniedException("You are not allowed to send messages to this chat");
        }
    }

}