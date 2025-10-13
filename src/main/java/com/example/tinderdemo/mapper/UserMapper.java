package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.UserDto;
import com.example.tinderdemo.model.like.UserMatchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    UserMatchDto toUserMatchDto(User user);
}
