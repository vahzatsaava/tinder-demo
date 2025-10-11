package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
