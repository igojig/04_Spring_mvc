package com.bellintegrator.spring_mvc_homework.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BillFormModel {
    private Long id;

    @NotNull(message = "сумма не должна быть пустой")
    @Positive(message = "сумма должна быть положительной")
    private Long amount;
}
