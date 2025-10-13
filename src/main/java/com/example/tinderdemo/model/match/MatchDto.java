package com.example.tinderdemo.model.match;

import com.example.tinderdemo.entity.enums.MatchStatus;
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
public class MatchDto {
    private String id;
    private UserMatchDto user1;
    private UserMatchDto user2;
    private MatchStatus matchStatus;
    private LocalDateTime matchedAt;
}
