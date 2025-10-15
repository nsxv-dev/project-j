package com.devCircle.devCircle.service;

import com.devCircle.devCircle.Dto.PostDto;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public List<PostDto> getAll() {
        return postRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PostDto getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return convertToDto(post);
    }

    public PostDto create(PostDto dto) {
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Post post = mapper.map(dto, Post.class);
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus("OPEN");

        Post saved = postRepository.save(post);
        return convertToDto(saved);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    private PostDto convertToDto(Post post) {
        PostDto dto = mapper.map(post, PostDto.class);
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorUsername(post.getAuthor().getUsername());

        return dto;
    }
}
