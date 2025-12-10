package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.CommentDto;
import com.devCircle.devCircle.entity.Comment;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.CommentRepository;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private final Mapper<Comment, CommentDto> commentMapper;
    private final NotificationService notificationService;

    @Transactional
    public CommentDto addComment(Long postId, CommentDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = commentMapper.toEntity(dto);
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        // Send notifications asynchronously to avoid transaction rollback issues
        try {
            notificationService.notifyFollowersOnNewComment(postId, comment);
            System.out.println(">>> Sending comment notification to " + post.getAuthor().getEmail());
            notificationService.sendCommentNotification(
                    post.getAuthor(),
                    "New comment on your post: " + post.getTitle()
                            + comment.getContent()
            );
        } catch (Exception e) {
            // log but don’t fail the comment save
            System.err.println("Failed to send notification: " + e.getMessage());
        }

        return commentMapper.toDto(savedComment);
    }


    public List<CommentDto> getComments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
