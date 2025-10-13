package com.example.tinderdemo.mapper;

import com.example.tinderdemo.entity.Match;
import com.example.tinderdemo.model.match.MatchDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface MatchMapper {

    MatchDto toMatchDto(Match match);

    List<MatchDto> toMatchDtoList(List<Match> likes);
}
