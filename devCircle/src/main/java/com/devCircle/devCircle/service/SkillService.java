package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.SkillDTO;
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
    private final Mapper<Skill, SkillDTO> skillMapper;

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(skillMapper::toDto)
                .toList();
    }
}
