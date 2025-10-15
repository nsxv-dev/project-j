package com.devCircle.devCircle.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;
    private String tags;   // e.g. "Spring Boot, Angular"

    @Column(nullable = false)
    private String type;   // OFFER or REQUEST
    private String status; // OPEN or CLOSED

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Ignorowanie lazy loading
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime createdAt;
}
