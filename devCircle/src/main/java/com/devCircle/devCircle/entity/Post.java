package com.devCircle.devCircle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String type;   // OFFER or REQUEST
    private String status; // OPEN or CLOSED

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Ignorowanie lazy loading
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
