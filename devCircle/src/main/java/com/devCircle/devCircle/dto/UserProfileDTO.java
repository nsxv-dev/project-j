package com.devCircle.devCircle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private String displayName;
    private String avatarUrl;
    private String email;
    private String role;
    private String githubUrl;
    private String linkedinUrl;
    private List<SkillDTO> skills;
}
