package com.example.tinderdemo.model.register;

import com.example.tinderdemo.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Email
    @Schema(example = "user@example.com", description = "Email пользователя")
    private String email;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    @Schema(example = "User", description = "Имя пользователя")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Age cannot be null")
    @Min(value = 16,message = "age should be over 16 years")
    @Max(value = 120,message = "age should be less than 120")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Bio cannot be blank")
    private String bio;

    @NotBlank(message = "Main photo cannot be blank")
    private String mainPhotoUrl;

    private List<String> photos;
}
