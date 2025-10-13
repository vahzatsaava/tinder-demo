package com.example.tinderdemo.model.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    @NotBlank
    private String chatId;
    @NotBlank
    private String content;
}
