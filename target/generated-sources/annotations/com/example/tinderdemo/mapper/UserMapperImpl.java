package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.UserDto;
import com.example.tinderdemo.model.like.UserMatchDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-13T15:17:03+0400",
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
    public UserMatchDto toUserMatchDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserMatchDto userMatchDto = new UserMatchDto();

        userMatchDto.setId( user.getId() );
        userMatchDto.setEmail( user.getEmail() );
        userMatchDto.setName( user.getName() );
        userMatchDto.setAge( user.getAge() );
        userMatchDto.setGender( user.getGender() );
        userMatchDto.setCity( user.getCity() );
        userMatchDto.setBio( user.getBio() );
        userMatchDto.setStatus( user.getStatus() );

        return userMatchDto;
    }
}
