package com.ninja.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{
    public String message;
    RecordNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
