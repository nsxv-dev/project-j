package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.UserProfileUpdateDTO;
import com.devCircle.devCircle.entity.Skill;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserProfileUpdateMapperImpl implements Mapper<User, UserProfileUpdateDTO> {
    private final ModelMapper modelMapper;
    private final SkillRepository skillRepository;

    @Override
    public User toEntity(UserProfileUpdateDTO dto) {
        User user = new User();

        user.setDisplayName(dto.getDisplayName());
        user.setAvatarUrl(dto.getAvatarUrl());
        user.setRole(dto.getRole());
        user.setGithubUrl(dto.getGithubUrl());
        user.setLinkedinUrl(dto.getLinkedinUrl());

        if (dto.getSkillIds() != null) {
            List<Skill> skills = dto.getSkillIds().stream()
                    .map(id -> skillRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Skill not found: " + id)))
                    .toList();

            user.setSkills(skills);
        }

        return user;
    }


    @Override
    public UserProfileUpdateDTO toDto(User user) {
        UserProfileUpdateDTO dto = modelMapper.map(user, UserProfileUpdateDTO.class);

        if (user.getSkills() != null) {
            dto.setSkillIds(
                    user.getSkills().stream()
                            .map(Skill::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
