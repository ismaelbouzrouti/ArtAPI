package org.enterpriseapp.artapi.exceptions;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserNameAlreadyExists(UserNameAlreadyExistsException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyExistsException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
