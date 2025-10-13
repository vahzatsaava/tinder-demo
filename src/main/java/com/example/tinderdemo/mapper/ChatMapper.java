package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.ChatEntity;
import com.example.tinderdemo.model.chat.ChatDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ChatMapper {
    ChatDto toChatDto(ChatEntity chatEntity);

    List<ChatDto> toChatDtos(List<ChatEntity> chatEntities);
}
