package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.UserDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements Mapper<User, UserDTO> {
    private final ModelMapper modelMapper;

    @Override
    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
