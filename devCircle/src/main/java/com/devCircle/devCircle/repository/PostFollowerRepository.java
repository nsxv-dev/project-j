package com.devCircle.devCircle.repository;

import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.PostFollower;
import com.devCircle.devCircle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFollowerRepository extends JpaRepository<PostFollower, Long> {
    boolean existsByUserAndPost(User user, Post post);

    List<PostFollower> findAllByUser(User user);

    List<PostFollower> findAllByPost(Post post);

    void deleteByUserAndPost(User user, Post post);
}
