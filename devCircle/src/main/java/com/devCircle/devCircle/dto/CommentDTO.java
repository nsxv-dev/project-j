package com.devCircle.devCircle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;

    @NotBlank(message = "Content is required")
    @Size(max = 255, message = "Content cannot exceed 255 characters")
    private String content;

    private LocalDateTime createdAt;

    private Long authorId;
    private String authorDisplayName;
}
