package com.example.tinderdemo.model.like;

import com.example.tinderdemo.entity.enums.Gender;
import com.example.tinderdemo.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMatchDto {
    private String id;
    private String email;
    private String name;
    private Integer age;
    private Gender gender;
    private String city;
    private String bio;
    private UserStatus status;
}
