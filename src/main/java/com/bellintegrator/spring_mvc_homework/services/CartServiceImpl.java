package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.Cart;
import com.bellintegrator.spring_mvc_homework.exceptions.CartNotFoundException;
import com.bellintegrator.spring_mvc_homework.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public List<Cart> getAllCartsByUserId(Long id) {
        log.info("Получаем список всех карт для пользователя с id: {}", id);
        return cartRepository.getAllCartsByUserId(id);
    }

    @Override
    @Transactional
    public Cart save(Cart cart) {
        log.info("Сохраняем карту");
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart createCart(Cart.CartStatus cartStatus, Cart.CartType cartType, Bill bill, Cart.PaymentSystem paymentSystem, Long balance) {
        log.info("Создаем карту типа {} для счета {}", cartType, bill.getId());
        Cart cart = new Cart();
        cart.setCartStatus(cartStatus);
        cart.setCartType(cartType);
        cart.setBill(bill);
        cart.setPaymentSystem(paymentSystem);
        cart.setBalance(balance);
        return cartRepository.save(cart);
    }

    @Override
    public Cart findById(Long id) {
        log.info("Ищем карту {}", id);
        return cartRepository.findById(id).orElseThrow(()->new CartNotFoundException("Карта не найдена, id="+ id));
    }

    @Override
    @Transactional
    public Cart setStatus(Long id, Cart.CartStatus cartStatus) {
        log.info("Устанавливаем статус {} для карты {}",cartStatus, id);
        Cart cart = findById(id);
        cart.setCartStatus(cartStatus);
        return cart;
    }
}
