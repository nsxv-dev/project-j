package com.devCircle.devCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    private String title;

    @Column(length = 2000)
    private String message;

    private boolean read;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private Long relatedPostId;

    private Long relatedCommentId;
}
