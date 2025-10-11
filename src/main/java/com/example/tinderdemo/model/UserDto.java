package com.example.tinderdemo.model;

import com.example.tinderdemo.entity.Gender;
import com.example.tinderdemo.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Integer age;
    private Gender gender;
    private String city;
    private String bio;
    private UserStatus status;
    private String mainPhotoUrl;
    private List<String> photos;
}
