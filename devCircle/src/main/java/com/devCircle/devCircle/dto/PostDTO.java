package com.devCircle.devCircle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    @NotEmpty(message = "Tags are required")
    private List<TagDTO> tags;
    @NotBlank(message = "Type is required (OFFER or REQUEST)")
    private String type;

    private String status;
    private LocalDateTime createdAt;

    private Long authorId;
    private String authorDisplayName;
    private String authorAvatarUrl;
}
