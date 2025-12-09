package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.SkillDto;
import com.devCircle.devCircle.entity.Skill;
import com.devCircle.devCircle.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SkillMapperImpl implements Mapper<Skill, SkillDto> {
    @Override
    public Skill toEntity(SkillDto dto) {
        if (dto == null) return null;
        Skill skill = new Skill();
        skill.setId(dto.getId());
        skill.setName(dto.getName());
        return skill;
    }

    @Override
    public SkillDto toDto(Skill skill) {
        if (skill == null) return null;
        return new SkillDto(skill.getId(), skill.getName());
    }
}
