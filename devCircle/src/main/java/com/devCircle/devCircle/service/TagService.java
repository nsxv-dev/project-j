package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.TagDTO;
import com.devCircle.devCircle.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> TagDTO.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
