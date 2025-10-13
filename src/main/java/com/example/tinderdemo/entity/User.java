package com.example.tinderdemo.entity;

import com.example.tinderdemo.entity.enums.Gender;
import com.example.tinderdemo.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String city;
    private String bio;
    private String role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "main_photo_url")
    private String mainPhotoUrl;

    @ElementCollection
    @CollectionTable(name = "user_photos", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "photo_url")
    private List<String> photos = new ArrayList<>();

}
