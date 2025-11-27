package com.devCircle.devCircle.repository;

import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);

    Page<Post> findByAuthor(User author, Pageable pageable);
}
