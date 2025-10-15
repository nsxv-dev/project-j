package com.devCircle.devCircle.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PostDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    @NotBlank(message = "Tags are required")
    private String tags;
    @NotBlank(message = "Type is required (OFFER or REQUEST)")
    private String type;

    private String status;
    private LocalDateTime createdAt;

    @NotNull(message = "AuthorId is required")
    private Long authorId;
    private String authorUsername;
}
