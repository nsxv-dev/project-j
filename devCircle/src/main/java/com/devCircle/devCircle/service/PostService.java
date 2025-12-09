package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.Filter.PostFilterRequest;
import com.devCircle.devCircle.dto.PostDto;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.PostStatus;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.PostSpecifications;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Mapper<Post, PostDto> postMapper;
    private final UserRepository userRepository;

    public Page<PostDto> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toDto);
    }

    public Page<PostDto> getPostsByLoggedInUser(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return postRepository.findByAuthor(author, pageable)
                .map(postMapper::toDto);
    }

    public PostDto getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toDto(post);
    }

    public PostDto createPost(PostDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postMapper.toEntity(dto);
        post.setAuthor(user);
        post.setStatus(PostStatus.OPEN);
        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Page<PostDto> filterPosts(PostFilterRequest filter, Pageable pageable) {

        var spec = Specification.allOf(
                PostSpecifications.hasKeyword(filter.getKeyword()),
                PostSpecifications.hasStatus(filter.getStatus()),
                PostSpecifications.hasTags(filter.getTagIds())
        );

        Page<Post> page = postRepository.findAll(spec, pageable);

        return page.map(postMapper::toDto);
    }

    public PostDto closePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setStatus(PostStatus.CLOSED);
        Post saved = postRepository.save(post);

        return postMapper.toDto(saved);
    }
}
