package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.Cart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartService {
    public List<Cart> getAllCartsByUserId(Long id);

    public Cart save(Cart cart);

    public Cart createCart(Cart.CartStatus cartStatus, Cart.CartType cartType, Bill bill, Cart.PaymentSystem paymentSystem, Long balance);

    Cart findById(Long id);

    Cart setStatus(Long id, Cart.CartStatus cartStatus);
}
