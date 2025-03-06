package com.bellintegrator.spring_mvc_homework.controllers;

import com.bellintegrator.spring_mvc_homework.dtos.UserDto;
import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.mappers.UserMapper;
import com.bellintegrator.spring_mvc_homework.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @GetMapping
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "/users/register";
    }

    @PostMapping
    public String register(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        log.info("UserDto: {}", userDto);
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки в модели при регистрации пользователя");
            return "/users/register";
        }

        if (userService.existsUserByEmail(userDto.getEmail())) {
            log.warn("Ошибка при регистрации: email {} уже существует", userDto.getEmail());
            bindingResult.rejectValue("email", "email exists", "пользователь с таким e-mail уже существует");
            return "/users/register";
        }

        User user = userMapper.toEntity(userDto);
        user = userService.save(user);
        log.info("Создали пользователя: email: {}, id: {}", user.getEmail(), user.getId());
        log.info("Попытка автологина");
        try {
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            log.warn("Exception during login after registration, {}", e.getMessage());
            return "redirect:/login";
        }
        log.info("Перенаправление пользователя в профиль");

//       а вот это не работает...
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto.getFirstName(), userDto.getPassword());
//        authToken.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("redirect");
        return "redirect:/users/userProfile";
    }
}
