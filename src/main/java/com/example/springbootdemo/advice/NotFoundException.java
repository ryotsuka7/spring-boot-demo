package com.example.springbootdemo.advice;

import com.example.springbootdemo.messages.ErrorMessage;

public class NotFoundException extends ApiBaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(ErrorMessage errorMessage, Object... args) {
        super(errorMessage, args);
    }
}
