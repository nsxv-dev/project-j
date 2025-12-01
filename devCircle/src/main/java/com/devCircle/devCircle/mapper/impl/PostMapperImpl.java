package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.dto.TagDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.Tag;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements Mapper<Post, PostDTO> {
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    @Override
    public Post toEntity(PostDTO dto) {
        Post post = modelMapper.map(dto, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("OPEN");

        if (dto.getTags() != null) {
            List<Tag> tagEntities = dto.getTags().stream()
                    .map(tagDto -> tagRepository.findById(tagDto.getId())
                            .orElseThrow(() -> new RuntimeException("Tag not found: " + tagDto.getId())))
                    .collect(Collectors.toList());
            post.setTags(tagEntities);
        }

        return post;
    }


    @Override
    public PostDTO toDto(Post entity) {
        PostDTO dto = modelMapper.map(entity, PostDTO.class);

        if (entity.getTags() != null) {
            List<TagDTO> tagDtos = entity.getTags().stream()
                    .map(tag -> TagDTO.builder()
                            .id(tag.getId())
                            .name(tag.getName())
                            .build())
                    .collect(Collectors.toList());
            dto.setTags(tagDtos);
        }

        dto.setAuthorDisplayName(entity.getAuthor().getDisplayName());
        dto.setAuthorAvatarUrl(entity.getAuthor().getAvatarUrl());
        return dto;
    }
}
