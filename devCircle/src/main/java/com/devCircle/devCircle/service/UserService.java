package com.devCircle.devCircle.service;

import com.devCircle.devCircle.dto.UserDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import com.devCircle.devCircle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper<User, UserDTO> userMapper;


    public UserDTO create(UserDTO dto) {
        User user = userMapper.mapFrom(dto);
        user.setRole("BEGINNER");

        return userMapper.mapTo(userRepository.save(user));
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapTo)
                .toList();
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.mapTo(user);
    }
}
