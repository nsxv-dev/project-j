package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.PostFollower;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.PostFollowerRepository;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class FollowController {

    private final PostRepository postRepository;
    private final PostFollowerRepository followerRepo;
    private final UserRepository userRepository;

    @PostMapping("/{postId}/follow")
    public void followPost(@PathVariable Long postId, Principal principal) {
        User current = userRepository.findByEmail(principal.getName()).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        if (!followerRepo.existsByUserAndPost(current, post)) {
            followerRepo.save(PostFollower.builder()
                    .user(current)
                    .post(post)
                    .build());
        }
    }

    @DeleteMapping("/{postId}/follow")
    public void unfollowPost(@PathVariable Long postId, Principal principal) {
        User current = userRepository.findByEmail(principal.getName()).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        followerRepo.deleteByUserAndPost(current, post);
    }
}
