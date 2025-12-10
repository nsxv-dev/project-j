package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.NotificationDto;
import com.devCircle.devCircle.entity.*;
import com.devCircle.devCircle.repository.NotificationRepository;
import com.devCircle.devCircle.repository.PostFollowerRepository;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PostRepository postRepository;
    private final PostFollowerRepository postFollowerRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public void sendCommentNotification(User postOwner, String message) {
        String principalName = postOwner.getEmail();
        String destination = "/queue/notifications";
        System.out.println(">>> Sending to /user/" + principalName + destination + ": " + message);

        messagingTemplate.convertAndSendToUser(
                postOwner.getEmail(),
                "/queue/notifications",
                message
        );
        messagingTemplate.convertAndSend("/topic/test", "Hello world");
    }

    public void notifyFollowersOnNewComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));

        List<PostFollower> followers = postFollowerRepository.findAllByPost(post);

        for (PostFollower pf : followers) {
            User follower = pf.getUser();

            if (follower.getId().equals(comment.getAuthor().getId())) continue;

            Notification notification = notificationRepository.save(
                    Notification.builder()
                            .user(follower)
                            .title("New comment on: " + post.getTitle())
                            .message(comment.getAuthor().getDisplayName()
                                    + " commented: " + excerpt(comment.getContent()))
                            .relatedPostId(post.getId())
                            .relatedCommentId(comment.getId())
                            .read(false)
                            .build()
            );

            messagingTemplate.convertAndSendToUser(
                    follower.getEmail(),              // important: must match Principal.getName()
                    "/queue/notifications",
                    NotificationDto.from(notification)
            );
        }
    }

    private String excerpt(String text) {
        if (text == null) return "";
        return text.length() <= 120 ? text : text.substring(0, 117) + "...";
    }

    public List<NotificationDto> getNotificationsForUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository
                .findAllByUserOrderByCreatedAtDesc(user, pageable)
                .stream()
                .map(NotificationDto::from)
                .toList();
    }

    public void markAsRead(Long id, User user) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if (!n.getUser().getId().equals(user.getId()))
            throw new AccessDeniedException("Not allowed");
        n.setRead(true);
        notificationRepository.save(n);
    }
}
