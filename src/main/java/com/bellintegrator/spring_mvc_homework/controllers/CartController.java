package com.bellintegrator.spring_mvc_homework.controllers;

import com.bellintegrator.spring_mvc_homework.dtos.CartDto;
import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.Cart;
import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.mappers.CartMapper;
import com.bellintegrator.spring_mvc_homework.model.CartFormModel;
import com.bellintegrator.spring_mvc_homework.services.BillService;
import com.bellintegrator.spring_mvc_homework.services.CartService;
import com.bellintegrator.spring_mvc_homework.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
@Slf4j
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final BillService billService;
    private final CartMapper cartMapper;
//    private final BillMapper billMapper;

    @GetMapping
    public String getAllCarts(Model model) {
        log.info("Выводим список всех карт");
        User user = userService.getAuthUser();
        List<Cart> carts = cartService.getAllCartsByUserId(user.getId());
        Map<Cart.CartStatus, List<CartDto>> cartMap = carts.stream()
                .map(cartMapper::toDto)
                .collect(Collectors.groupingBy(CartDto::getCartStatus));

        model.addAttribute("carts", cartMap);
        return "carts/listAllCarts";
    }

    @GetMapping("/openDebit")
    public String openDebitCart(Model model) {
        log.info("Передаем View для открытия дебитовой карты");
        User user = userService.getAuthUser();
        Optional<Bill> bill = billService.getBillByUserIdAndType(user.getId(), Bill.BillType.DEBIT_CART);
        CartFormModel cartFormModel = new CartFormModel();
        if (bill.isEmpty()) {
            cartFormModel.setCreateBill(true);
        } else {
            cartFormModel.setCreateBill(false);
            cartFormModel.setBalance(bill.get().getBalance()); // для прохода валидации
            cartFormModel.setBillId(bill.get().getId());
        }

        model.addAttribute("cartFormModel", cartFormModel);
        return "carts/createDebitCart";
    }

    @PostMapping("/openDebit")
    public String openDebitCart(@Valid CartFormModel cartFormModel, BindingResult bindingResult) {
        log.info("Получили данные из View для открытия дебитовой карты");
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки в модели при создании дебетовой карты");
            return "carts/createDebitCart";
        }

        if (cartFormModel.isCreateBill()) {
            Bill bill = billService.createBill(Bill.BillType.DEBIT_CART, cartFormModel.getBalance(), userService.getAuthUser());
            cartService.createCart(Cart.CartStatus.ACTIVE, Cart.CartType.DEBIT, bill, cartFormModel.getPaymentSystem(), cartFormModel.getBalance());
        } else {
            Bill bill = billService.findById(cartFormModel.getBillId());
            cartService.createCart(Cart.CartStatus.ACTIVE, Cart.CartType.DEBIT, bill, cartFormModel.getPaymentSystem(), bill.getBalance());
        }

        return "redirect:/carts";
    }

    @GetMapping("/openCredit")
    public String openCredit(Model model) {
        log.info("Передаем View для открытия кредитной карты");
        CartFormModel cartFormModel = new CartFormModel();
        model.addAttribute("cartFormModel", cartFormModel);
        return "carts/createCreditCart";
    }

    @PostMapping("/openCredit")
    public String openCredit(@Valid CartFormModel cartFormModel, BindingResult bindingResult) {
        log.info("Получили данные из View для открытия кредитной карты");
        if (bindingResult.hasErrors()) {
            log.info("Ошибка в модели при создании кредитной карты");
            return "carts/createCreditCart";
        }

        User authUser = userService.getAuthUser();
        Bill bill = billService.createBill(Bill.BillType.CREDIT_CART, cartFormModel.getBalance(), authUser);
        Cart cart = cartService.createCart(Cart.CartStatus.ACTIVE, Cart.CartType.CREDIT, bill, cartFormModel.getPaymentSystem(), cartFormModel.getBalance());
        log.info("Сохранили кредитную карту с id: {}", cart.getId());
        return "redirect:/carts";
    }

    @PostMapping("/block")
    public String block(@RequestParam(name = "cartId") Long id) {
        Cart cart=cartService.setStatus(id, Cart.CartStatus.BLOCKED);
        return "redirect:/carts";
    }

    @PostMapping("/close")
    public String close(@RequestParam(name = "cartId") Long id) {
        Cart cart=cartService.setStatus(id, Cart.CartStatus.CLOSED);
        return "redirect:/carts";
    }
}
