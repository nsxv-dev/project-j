package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.impl.PostMapperImpl;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapperImpl postMapper;
    private final UserRepository userRepository;

    public List<PostDTO> getAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getPostsByLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return postRepository.findByAuthor(author)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDTO getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toDto(post);
    }

    public PostDTO createPost(PostDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postMapper.toEntity(dto);
        post.setAuthor(user);
        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
