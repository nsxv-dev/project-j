package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.Filter.PostFilterRequest;
import com.devCircle.devCircle.dto.PostDTO;
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

    // -------------------------------------------------------------
    // Public post listing
    // -------------------------------------------------------------
    @GetMapping
    public Page<PostDTO> getPosts(Pageable pageable) {
        return postService.getPosts(defaultSorted(pageable));
    }

    // -------------------------------------------------------------
    // Posts created by the logged-in user
    // -------------------------------------------------------------
    @GetMapping("/my")
    public Page<PostDTO> getMyPosts(Pageable pageable) {
        return postService.getPostsByLoggedInUser(defaultSorted(pageable));
    }

    // -------------------------------------------------------------
    // Get single post by ID (numeric only)
    // -------------------------------------------------------------
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    // -------------------------------------------------------------
    // Create a new post
    // -------------------------------------------------------------
    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }

    // -------------------------------------------------------------
    // Delete a post
    // -------------------------------------------------------------
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------
    // Filter endpoint (POST to avoid URL bloat)
    // -------------------------------------------------------------
    @PostMapping("/filter")
    public Page<PostDTO> filterPosts(
            @RequestBody PostFilterRequest filter,
            Pageable pageable
    ) {
        return postService.filterPosts(filter, defaultSorted(pageable));
    }

    // -------------------------------------------------------------
    // Utility: Set default sorting across all list endpoints
    // -------------------------------------------------------------
    private Pageable defaultSorted(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("createdAt").descending()
        );
    }
}

