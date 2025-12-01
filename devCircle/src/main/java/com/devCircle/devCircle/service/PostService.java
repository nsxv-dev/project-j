package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Mapper<Post, PostDTO> postMapper;
    private final UserRepository userRepository;

    public Page<PostDTO> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toDto);
    }

    public Page<PostDTO> getPostsByLoggedInUser(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return postRepository.findByAuthor(author, pageable)
                .map(postMapper::toDto);
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
