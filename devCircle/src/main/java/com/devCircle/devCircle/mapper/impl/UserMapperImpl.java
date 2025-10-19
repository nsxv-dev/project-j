package com.devCircle.devCircle.mapper.impl;

import com.devCircle.devCircle.dto.UserDTO;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<User, UserDTO> {
    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;


    @Override
    public User mapFrom(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO mapTo(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
