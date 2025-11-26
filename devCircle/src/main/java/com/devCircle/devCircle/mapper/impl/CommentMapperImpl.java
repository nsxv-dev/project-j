package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.CommentDTO;
import com.devCircle.devCircle.entity.Comment;
import com.devCircle.devCircle.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements Mapper<Comment, CommentDTO> {
    public CommentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;

    @Override
    public Comment toEntity(CommentDTO dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        CommentDTO dto = modelMapper.map(entity, CommentDTO.class);
        dto.setAuthorDisplayName(dto.getAuthorDisplayName());
        dto.setAuthorAvatarUrl(dto.getAuthorAvatarUrl());
        return dto;
    }
}
