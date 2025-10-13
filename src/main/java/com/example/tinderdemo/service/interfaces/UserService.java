package com.example.tinderdemo.service.interfaces;

import com.example.tinderdemo.entity.User;
import com.example.tinderdemo.model.UserDto;
import com.example.tinderdemo.model.UserUpdateDto;
import com.example.tinderdemo.model.register.AuthResponse;
import com.example.tinderdemo.model.register.UserAuthRequest;
import com.example.tinderdemo.model.register.UserRegisterRequest;
import java.security.Principal;

public interface UserService {
    AuthResponse register(UserRegisterRequest request);
    AuthResponse login(UserAuthRequest request);
    AuthResponse refreshAccessToken(String currentToken);
    UserDto getAccount(Principal principal);
    void deleteAccount(Principal principal);
    UserDto updateAccount(Principal principal, UserUpdateDto userUpdateDto);
    User findUserByEmail(String userEmail);
}
