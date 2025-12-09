package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.SkillDto;
import com.devCircle.devCircle.entity.Skill;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final Mapper<Skill, SkillDto> skillMapper;

    public List<SkillDto> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(skillMapper::toDto)
                .toList();
    }
}
