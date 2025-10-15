package com.devCircle.devCircle.repository;

import com.devCircle.devCircle.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
