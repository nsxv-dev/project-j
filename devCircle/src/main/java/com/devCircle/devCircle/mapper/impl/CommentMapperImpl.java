package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.CommentDto;
import com.devCircle.devCircle.entity.Comment;
import com.devCircle.devCircle.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements Mapper<Comment, CommentDto> {
    private final ModelMapper modelMapper;

    @Override
    public Comment toEntity(CommentDto dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDto toDto(Comment entity) {
        CommentDto dto = modelMapper.map(entity, CommentDto.class);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorDisplayName(dto.getAuthorDisplayName());
        dto.setAuthorAvatarUrl(dto.getAuthorAvatarUrl());
        return dto;
    }
}
