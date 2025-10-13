package com.example.tinderdemo.model.chat;

import com.example.tinderdemo.model.like.UserMatchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private String id;
    private UserMatchDto fromUser;
    private UserMatchDto toUser;
    private LocalDateTime createdAt;
}
