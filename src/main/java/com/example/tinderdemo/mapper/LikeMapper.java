package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.Like;
import com.example.tinderdemo.model.like.LikeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface LikeMapper {

    @Mapping(source = "fromUser", target = "fromUser")
    @Mapping(source = "toUser", target = "toUser")
    LikeDto toLikeDto(Like like);

    List<LikeDto> toLikeDtoList(List<Like> likes);
}
