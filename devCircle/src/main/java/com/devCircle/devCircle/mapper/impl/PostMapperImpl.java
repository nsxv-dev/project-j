package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapperImpl implements Mapper<Post, PostDTO> {
    public PostMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;

    @Override
    public Post toEntity(PostDTO dto) {
        Post post = modelMapper.map(dto, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("OPEN");
        return post;
    }

    @Override
    public PostDTO toDto(Post entity) {
        PostDTO dto = modelMapper.map(entity, PostDTO.class);
        dto.setAuthorDisplayName(dto.getAuthorDisplayName());
        return dto;
    }
}
