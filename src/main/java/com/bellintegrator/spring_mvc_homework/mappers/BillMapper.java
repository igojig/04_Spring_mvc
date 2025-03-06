package com.bellintegrator.spring_mvc_homework.mappers;

import com.bellintegrator.spring_mvc_homework.dtos.BillDto;
import com.bellintegrator.spring_mvc_homework.dtos.UserDto;
import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.model.CartFormModel;
import com.bellintegrator.spring_mvc_homework.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BillMapper {

   @Autowired
   protected UserService userService;

   @Mapping(target = "userDto", ignore = true, source = "user")
   public abstract BillDto toDto(Bill bill);

   @Mapping(target = "user", source = "userDto")
   public abstract Bill toEntity(BillDto billDto);

   @Mapping(target = "type", constant = "DEPOSIT")
   @Mapping(target = "user", expression = "java(this.getAuthUser())")
   public abstract Bill toEntityWithDepositTypeAndAuthUser(BillDto billDto);

//   @Mapping(target = "id", source = "billId", ignore = true)
//   @Mapping(target = "type", constant = "DEBIT_CART")
//   @Mapping(target ="user", expression = "java(this.getAuthUser())")
//   public abstract Bill toEntityFromCartModelWithTypeDebitCartAndAuthUser(CartFormModel model);

   @Named("getAuthUser")
   protected User getAuthUser(){
      return userService.getAuthUser();
   }
}
