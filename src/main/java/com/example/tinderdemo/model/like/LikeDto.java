package com.example.tinderdemo.model.like;

import com.example.tinderdemo.entity.enums.LikeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private String id;
    private UserMatchDto fromUser;
    private UserMatchDto toUser;
    private LikeType likeStatus;
}
