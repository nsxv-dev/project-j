package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.UserDTO;
import com.devCircle.devCircle.dto.UserProfileDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper<User, UserDTO> userMapper;
    private final Mapper<User, UserProfileDTO> userProfileMapper;


    public UserDTO create(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        user.setRole("BEGINNER");

        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserProfileDTO getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProfileMapper.toDto(user);
    }

    public UserProfileDTO getCurrentUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProfileMapper.toDto(user);
    }

    //Helpers
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByDisplayName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    }
}
