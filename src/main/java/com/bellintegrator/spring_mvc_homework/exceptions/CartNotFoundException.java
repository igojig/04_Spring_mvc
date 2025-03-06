package com.bellintegrator.spring_mvc_homework.exceptions;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String message){
        super(message);
    }
}
