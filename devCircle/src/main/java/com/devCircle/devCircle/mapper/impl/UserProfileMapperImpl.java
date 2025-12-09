package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.SkillDto;
import com.devCircle.devCircle.dto.UserProfileDto;
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
public class UserProfileMapperImpl implements Mapper<User, UserProfileDto> {
    private final ModelMapper modelMapper;
    private final SkillRepository skillRepository;

    @Override
    public User toEntity(UserProfileDto dto) {
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
    public UserProfileDto toDto(User user) {

        UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);
        if (user.getSkills() != null) {
            List<SkillDto> skillDtos = user.getSkills().stream()
                    .map(skill -> SkillDto.builder()
                            .id(skill.getId())
                            .name(skill.getName())
                            .build())
                    .collect(Collectors.toList());
            dto.setSkills(skillDtos);
        }
        return dto;
    }
}
