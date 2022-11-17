package com.example.springbootdemo.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        // リクエストのBodyのセット
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(0, ex.getMessage(), path);
        
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex,
        WebRequest request) {
        // リクエストのBodyのセット
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(ex.getCode(), ex.getMessage(), path);

        // リクエストヘッダーの作成
        HttpHeaders httpHeaders = new HttpHeaders();

        return handleExceptionInternal(ex, apiError, httpHeaders, HttpStatus.NOT_FOUND, request);
    }

}
