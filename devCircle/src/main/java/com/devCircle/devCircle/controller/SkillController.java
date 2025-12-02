package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.SkillDTO;
import com.devCircle.devCircle.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }
}
