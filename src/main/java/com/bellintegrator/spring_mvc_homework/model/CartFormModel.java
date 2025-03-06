package com.bellintegrator.spring_mvc_homework.model;

import com.bellintegrator.spring_mvc_homework.entities.Cart;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartFormModel {
    @NotNull(message = "баланс не должен быть пустым")
    @Positive(message = "баланс должен быть положительным")
    private Long balance;

    private boolean createBill;
    private Long billId;

    @NotNull(message = "платежная система должнабыть выбрана")
    private Cart.PaymentSystem paymentSystem;
}
