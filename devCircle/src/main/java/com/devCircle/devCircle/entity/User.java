package com.devCircle.devCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String email;
    private String password;

    @Column(nullable = false)
    private String role;  // e.g. BEGINNER, MENTOR, COLLABORATOR

    private String skills;     // comma-separated for now (later: separate table)
    private String githubUrl;
    private String linkedinUrl;
}
