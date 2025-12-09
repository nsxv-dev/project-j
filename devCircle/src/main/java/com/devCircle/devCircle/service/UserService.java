package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.UserDto;
import com.devCircle.devCircle.dto.UserProfileDto;
import com.devCircle.devCircle.dto.UserProfileUpdateDto;
import com.devCircle.devCircle.entity.Skill;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.SkillRepository;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<User, UserProfileDto> userProfileMapper;
    private final Mapper<User, UserProfileUpdateDto> userProfileUpdateMapper;


    public UserDto create(UserDto dto) {
        User user = userMapper.toEntity(dto);
        user.setRole("BEGINNER");

        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserProfileDto getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProfileMapper.toDto(user);
    }

    public UserProfileDto getCurrentUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userProfileMapper.toDto(user);
    }

    public UserProfileDto updateCurrentUserProfile(UserProfileUpdateDto dto) {
        // Get current authenticated user's email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch user or throw a specific exception
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email '" + email + "' not found"));

        // Map DTO to entity for easier access to new values
        User updateData = userProfileUpdateMapper.toEntity(dto);

        // Partial updates: only update non-null fields
        Optional.ofNullable(updateData.getDisplayName()).ifPresent(user::setDisplayName);
        Optional.ofNullable(updateData.getRole()).ifPresent(user::setRole);
        Optional.ofNullable(updateData.getAvatarUrl()).ifPresent(user::setAvatarUrl);
        Optional.ofNullable(updateData.getGithubUrl()).ifPresent(user::setGithubUrl);
        Optional.ofNullable(updateData.getLinkedinUrl()).ifPresent(user::setLinkedinUrl);

        // Update skills if provided
        if (dto.getSkillIds() != null) {
            List<Skill> newSkills = skillRepository.findAllById(dto.getSkillIds());
            user.getSkills().clear();
            user.getSkills().addAll(newSkills);
        }

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Return updated DTO
        return userProfileMapper.toDto(updatedUser);
    }


    //Helpers
    public UserDto findByUsername(String username) {
        User user = userRepository.findByDisplayName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    }
}
