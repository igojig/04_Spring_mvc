package com.bellintegrator.spring_mvc_homework.exceptions;

public class BillLimitException extends RuntimeException{

    public BillLimitException(String message){
        super(message);
    }
}
