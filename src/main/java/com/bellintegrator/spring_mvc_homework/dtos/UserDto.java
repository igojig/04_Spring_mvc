package com.bellintegrator.spring_mvc_homework.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "поле не должно быть пустым")
    private String firstName;

    @NotBlank(message = "поле не должно быть пустым")
    private String lastName;

    @Pattern(regexp = "(\\d{4})\\s*-?\\s*(\\d{6})",
            message = "паспорт должен содержать цифры в формате [XXXX XXXXXX]")
    private String passport;

    @NotBlank(message = "поле не должно быть пустым")
    @Size(min = 4, message = "длина пароля должна быть больше 4")
    private String password;

    @NotNull
    @Past(message = "дата должна быть меньше текущей")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Email(message = "must be in email format")
    @NotBlank(message = "поле не должно быть пустым")
    private String email;
}
