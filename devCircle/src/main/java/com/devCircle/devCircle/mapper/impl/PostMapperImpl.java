package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapperImpl implements Mapper<Post, PostDTO> {
    public PostMapperImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public Post mapFrom(PostDTO dto) {
        Long id = ((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        Post post = modelMapper.map(dto, Post.class);
        User user = userRepository.findById(id)
                .orElseThrow();
        post.setAuthor(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("OPEN");
        return post;
    }

    @Override
    public PostDTO mapTo(Post entity) {
        PostDTO dto = modelMapper.map(entity, PostDTO.class);
        dto.setAuthorUsername(dto.getAuthorUsername());
        return dto;
    }
}
