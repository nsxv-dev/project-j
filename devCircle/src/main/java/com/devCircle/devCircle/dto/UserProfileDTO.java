package com.devCircle.devCircle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private String displayName;
    private String avatarUrl;
    private String email;
    private String role;
    private String skills;
    private String githubUrl;
    private String linkedinUrl;
}
