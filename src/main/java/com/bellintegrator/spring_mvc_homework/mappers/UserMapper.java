package com.bellintegrator.spring_mvc_homework.mappers;


import com.bellintegrator.spring_mvc_homework.dtos.UserDto;
import com.bellintegrator.spring_mvc_homework.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", constant = "[PROTECTED]")
    public abstract UserDto toDto(User user);

    @Mapping(target = "password", qualifiedByName = "getHashPassword")
    public abstract User toEntity(UserDto userDto);

    @Named("getHashPassword")
    protected String getHashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
