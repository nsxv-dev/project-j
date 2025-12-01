package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.SkillDTO;
import com.devCircle.devCircle.dto.UserProfileDTO;
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
public class UserProfileMapperImpl implements Mapper<User, UserProfileDTO> {
    private final ModelMapper modelMapper;
    private final SkillRepository skillRepository;

    @Override
    public User toEntity(UserProfileDTO dto) {
        User user = modelMapper.map(dto, User.class);

        if (dto.getSkills() != null) {
            List<Skill> skillEntities = dto.getSkills().stream()
                    .map(skillDto -> skillRepository.findById(skillDto.getId())
                            .orElseThrow(() -> new RuntimeException("Skill not found: " + skillDto.getId())))
                    .collect(Collectors.toList());
            user.setSkills(skillEntities);
        }
        return user;
    }

    @Override
    public UserProfileDTO toDto(User user) {

        UserProfileDTO dto = modelMapper.map(user, UserProfileDTO.class);
        if (user.getSkills() != null) {
            List<SkillDTO> skillDtos = user.getSkills().stream()
                    .map(skill -> SkillDTO.builder()
                            .id(skill.getId())
                            .name(skill.getName())
                            .build())
                    .collect(Collectors.toList());
            dto.setSkills(skillDtos);
        }
        return dto;
    }
}
