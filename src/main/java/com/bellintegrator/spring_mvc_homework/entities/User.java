package com.bellintegrator.spring_mvc_homework.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name ="date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "passport", nullable = false)
    private String passport;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
