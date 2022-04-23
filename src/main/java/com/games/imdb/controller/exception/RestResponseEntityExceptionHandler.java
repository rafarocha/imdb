package com.games.imdb.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneral(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "falha geral da aplicacao \n\n" + ex.getMessage();
        log.error("falha geral ", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, 
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
        String bodyOfResponse = "falha negocio \n\n" + ex.getMessage();
        log.error("falha negocio ", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, 
            new HttpHeaders(), HttpStatus.valueOf(422), request);
    }
    
}