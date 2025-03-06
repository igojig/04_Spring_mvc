package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.User;

import java.util.List;

public interface UserService {

     User save(User user);

     boolean existsUserByEmail(String email);

     User getAuthUser();
}
