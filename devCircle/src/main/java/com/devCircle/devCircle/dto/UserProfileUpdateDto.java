package com.devCircle.devCircle.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileUpdateDto {
    private String displayName;
    private String role;
    private String githubUrl;
    private String linkedinUrl;
    private String avatarUrl;
    private List<Long> skillIds;
}