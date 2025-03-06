package com.bellintegrator.spring_mvc_homework.exceptions;

public class BillNotFoundException extends RuntimeException{
    public BillNotFoundException(String message) {
        super(message);
    }
}
