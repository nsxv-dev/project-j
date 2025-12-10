package com.devCircle.devCircle.dto;

import com.devCircle.devCircle.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long id;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
    private Long relatedPostId;
    private Long relatedCommentId;

    public static NotificationDto from(Notification n) {
        return NotificationDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .message(n.getMessage())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .relatedPostId(n.getRelatedPostId())
                .relatedCommentId(n.getRelatedCommentId())
                .build();
    }
}

