package com.example.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//Configuración experimenatal son execpciones globales para eliminar el trycath repetitivo si no funciona eliminar y seguir con try catch en controllers
@ControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(RuntimeException.class)
 public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
    return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
 }
}
