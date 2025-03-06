package com.bellintegrator.spring_mvc_homework.dtos;

import com.bellintegrator.spring_mvc_homework.entities.Cart;
import lombok.Data;

import java.util.UUID;

@Data
public class CartDto {
    private Long id;
    private Long balance;
    private Cart.CartStatus cartStatus;
    private Cart.PaymentSystem paymentSystem;
    private Cart.CartType cartType;
    private BillDto billDto;
}
