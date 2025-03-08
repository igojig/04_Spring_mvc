package com.bellintegrator.spring_mvc_homework.dtos;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BillDto {
    private Long id;
    private Bill.BillType billType;
    private UserDto userDto;

    @NotNull(message = "баланс не должен быть пустым")
    @Positive(message = "баланс должен  быть положительным")
    private Long balance;
}
