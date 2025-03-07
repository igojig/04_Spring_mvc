package com.bellintegrator.spring_mvc_homework.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BillLimitException.class)
    public String billLimitException(Exception ex, Model model) {
        log.info("Перехватили ошибку: {}", ex.getMessage());
        model.addAttribute("cause", "Ошибка при снятии денег");
        model.addAttribute("error", ex.getMessage());
        return "errors/programError";
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(Exception ex, Model model) {
        log.info("Перехватили ошибку: {}", ex.getMessage());
        model.addAttribute("cause", "Ошибка в базе данных");
        model.addAttribute("error", ex.getMessage());
        return "errors/programError";
    }

    @ExceptionHandler(BillNotFoundException.class)
    public String billNotFoundException(Exception ex, Model model) {
        log.info("Перехватили ошибку: {}", ex.getMessage());
        model.addAttribute("cause", "Ошибка при поиске счета");
        model.addAttribute("error", ex.getMessage());
        return "errors/programError";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(Exception ex, Model model) {
        log.info("Перехватили ошибку: {}", ex.getMessage());
        model.addAttribute("cause", "Ошибка при поиске ползователя");
        model.addAttribute("error", ex.getMessage());
        return "errors/programError";
    }

    @ExceptionHandler(CartNotFoundException.class)
    public String cartNotFoundException(Exception ex, Model model) {
        log.info("Перехватили ошибку: {}", ex.getMessage());
        model.addAttribute("cause", "Ошибка при поиске карты");
        model.addAttribute("error", ex.getMessage());
        return "errors/programError";
    }
}
