package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T12:33:26+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setName( user.getName() );
        userDto.setAge( user.getAge() );
        userDto.setGender( user.getGender() );
        userDto.setCity( user.getCity() );
        userDto.setBio( user.getBio() );
        userDto.setStatus( user.getStatus() );
        userDto.setMainPhotoUrl( user.getMainPhotoUrl() );
        List<String> list = user.getPhotos();
        if ( list != null ) {
            userDto.setPhotos( new ArrayList<String>( list ) );
        }

        return userDto;
    }

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setEmail( userDto.getEmail() );
        user.setPassword( userDto.getPassword() );
        user.setName( userDto.getName() );
        user.setAge( userDto.getAge() );
        user.setGender( userDto.getGender() );
        user.setCity( userDto.getCity() );
        user.setBio( userDto.getBio() );
        user.setStatus( userDto.getStatus() );
        user.setMainPhotoUrl( userDto.getMainPhotoUrl() );
        List<String> list = userDto.getPhotos();
        if ( list != null ) {
            user.setPhotos( new ArrayList<String>( list ) );
        }

        return user;
    }
}
