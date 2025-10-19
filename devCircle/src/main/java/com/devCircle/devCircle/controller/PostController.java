package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        System.out.println(((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).toString());
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO dto) {
        System.out.println(((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());

        return ResponseEntity.ok(postService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
