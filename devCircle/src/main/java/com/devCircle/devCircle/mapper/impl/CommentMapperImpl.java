package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.CommentDTO;
import com.devCircle.devCircle.entity.Comment;
import com.devCircle.devCircle.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements Mapper<Comment, CommentDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Comment toEntity(CommentDTO dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        CommentDTO dto = modelMapper.map(entity, CommentDTO.class);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorDisplayName(dto.getAuthorDisplayName());
        dto.setAuthorAvatarUrl(dto.getAuthorAvatarUrl());
        return dto;
    }
}
