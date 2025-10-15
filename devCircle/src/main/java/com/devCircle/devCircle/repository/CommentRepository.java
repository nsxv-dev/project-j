package com.devCircle.devCircle.repository;

import com.devCircle.devCircle.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
