package com.example.tinderdemo.model.chat;

import com.example.tinderdemo.model.like.UserMatchDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDto {
    private String id;
    private UserMatchDto sender;
    private String chatId;
    private String content;
    private LocalDateTime sentAt;
}
