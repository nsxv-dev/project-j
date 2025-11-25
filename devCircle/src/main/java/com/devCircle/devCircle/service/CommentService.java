package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.CommentDTO;
import com.devCircle.devCircle.entity.Comment;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.impl.CommentMapperImpl;
import com.devCircle.devCircle.repository.CommentRepository;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapperImpl commentMapper;

    public CommentDTO addComment(Long postId, CommentDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = commentMapper.toEntity(dto);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(user);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    public List<CommentDTO> getComments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
