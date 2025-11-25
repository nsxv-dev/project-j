package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.CommentDTO;
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
    public List<CommentDTO> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PostMapping
    public CommentDTO addComment(@PathVariable Long postId, @RequestBody CommentDTO dto) {
        return commentService.addComment(postId, dto);
    }
}
