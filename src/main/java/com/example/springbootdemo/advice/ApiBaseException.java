package com.example.springbootdemo.advice;

import com.example.springbootdemo.messages.ErrorMessage;
import java.text.MessageFormat;

public class ApiBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Integer code;

    public ApiBaseException(ErrorMessage errorMessage, Object... args) {
        super(MessageFormat.format(errorMessage.getMessage(), args));
        this.code = errorMessage.getCode();
    }

    public Integer getCode() {
        return code;
    }

}
