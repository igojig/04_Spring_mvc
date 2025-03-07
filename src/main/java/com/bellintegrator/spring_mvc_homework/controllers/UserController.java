package com.bellintegrator.spring_mvc_homework.controllers;

import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // профиль
    @GetMapping("/userProfile")
    public String userInfo(Model model) {
        User authUser = userService.getAuthUser();
        model.addAttribute("user", authUser);
        return "users/userProfile";
    }

    // личный кабинет
    @GetMapping("/userAccount")
    public String userAccount(Model model) {
        User authUser = userService.getAuthUser();
        model.addAttribute("firstName", authUser.getFirstName());
        model.addAttribute("lastName", authUser.getLastName());
        return "users/userAccount";
    }
}
