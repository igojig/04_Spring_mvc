package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.exceptions.UserNotFoundException;
import com.bellintegrator.spring_mvc_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        log.info("Создали пользователя: email {}, id {}", user.getEmail(), user.getId());
        return userRepository.save(user);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        log.info("Проверяем, существует ли пользователь с e-mail: {}", email);
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User getAuthUser() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        String emailAuthUser = authUser.getName();
        log.info("Получаем обьект залогиненного пользователя c email: {} ", emailAuthUser);
        return userRepository.findByEmail(emailAuthUser).
                orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + emailAuthUser));
    }
}
