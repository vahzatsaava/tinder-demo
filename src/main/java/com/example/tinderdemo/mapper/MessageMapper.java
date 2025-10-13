package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.MessageEntity;
import com.example.tinderdemo.model.chat.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChatMapper.class,UserMapper.class})
public interface MessageMapper {

    @Mapping(source = "chat.id", target = "chatId")
    MessageDto toMessageDto(MessageEntity message);

    List<MessageDto> toMessageDtos(List<MessageEntity> messageEntities);
}
