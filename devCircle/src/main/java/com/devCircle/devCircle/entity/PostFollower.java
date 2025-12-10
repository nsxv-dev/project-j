package com.devCircle.devCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "post_followers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Post post;
}
