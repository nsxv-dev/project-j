package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.Filter.PostFilterRequest;
import com.devCircle.devCircle.dto.PostDto;
import com.devCircle.devCircle.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<PostDto> getPosts(Pageable pageable) {
        return postService.getPosts(defaultSorted(pageable));
    }

    @GetMapping("/my")
    public Page<PostDto> getMyPosts(Pageable pageable) {
        return postService.getPostsByLoggedInUser(defaultSorted(pageable));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public Page<PostDto> filterPosts(
            @RequestBody PostFilterRequest filter,
            Pageable pageable
    ) {
        return postService.filterPosts(filter, defaultSorted(pageable));
    }

    @PatchMapping("/{id:\\d+}/close")
    public PostDto closePost(@PathVariable Long id) {
        return postService.closePost(id);
    }

    // Utility: Set default sorting across all list endpoints
    private Pageable defaultSorted(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("createdAt").descending()
        );
    }
}

