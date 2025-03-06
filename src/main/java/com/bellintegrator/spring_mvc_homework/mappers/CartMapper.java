package com.bellintegrator.spring_mvc_homework.mappers;

import com.bellintegrator.spring_mvc_homework.dtos.CartDto;
import com.bellintegrator.spring_mvc_homework.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CartMapper {

    @Mapping(target = "billDto", ignore = true, source = "bill")
    public abstract CartDto toDto(Cart cart);

    @Mapping(target = "bill", source = "billDto")
    @Mapping(target = "bill.user", source = "billDto.userDto")
    public abstract Cart toEntity(CartDto cartDto);

}
