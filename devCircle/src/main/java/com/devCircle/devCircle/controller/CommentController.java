package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.CommentDto;
import com.devCircle.devCircle.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PostMapping
    public CommentDto addComment(@PathVariable Long postId, @RequestBody CommentDto dto) {
        return commentService.addComment(postId, dto);
    }
}
