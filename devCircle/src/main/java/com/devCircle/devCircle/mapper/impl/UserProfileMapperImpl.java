package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.UserProfileDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapperImpl implements Mapper<User, UserProfileDTO> {
    public UserProfileMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;

    @Override
    public User toEntity(UserProfileDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserProfileDTO toDto(User user) {
        return modelMapper.map(user, UserProfileDTO.class);
    }
}
