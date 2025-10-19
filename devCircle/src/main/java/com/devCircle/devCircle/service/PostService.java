package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.PostDTO;
import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.mapper.impl.PostMapperImpl;
import com.devCircle.devCircle.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapperImpl postMapper;
    private final JwtService jwtService;

    public List<PostDTO> getAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapTo)
                .collect(Collectors.toList());
    }

    public PostDTO getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.mapTo(post);
    }

    public PostDTO create(PostDTO dto) {
        //znjadz id z kontekstu
        //
        Post saved = postRepository.save(postMapper.mapFrom(dto));
        return postMapper.mapTo(saved);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
