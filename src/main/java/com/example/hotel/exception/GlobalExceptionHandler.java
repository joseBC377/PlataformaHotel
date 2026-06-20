package com.example.hotel.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//Configuración experimenatal son execpciones globales para eliminar el trycath repetitivo si no funciona eliminar y seguir con try catch en controllers
@ControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(RuntimeException.class)
 public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
    return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
 }
 
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
    Map<String, String> errores = new HashMap<>();

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error -> {
          errores.put(
              error.getField(),
              error.getDefaultMessage()
          );
      });

    return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
 }

}
