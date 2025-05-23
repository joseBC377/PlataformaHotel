package com.example.hotel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.services.AuthenticationService;
import com.example.hotel.util.AuthenticationRequest;
import com.example.hotel.util.AuthenticationResponse;
import com.example.hotel.util.RefreshTokenRequest;
import com.example.hotel.util.RegisterRequest;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Se podria decir que con este controlador generamos token de acceso y refrezco
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //El AuthenticationRespones viene del paquete util

    //El RegisterRequest viene de el paquete util , el record RegisterRequest
    @PostMapping("/registro")
    public ResponseEntity <AuthenticationResponse> registrar(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    
    
    @PostMapping("/autenticarse")
    public ResponseEntity <AuthenticationResponse> authenticar (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    

    //Con este solicitamos con nuestro token refresh un nuevo token de access
    @PostMapping("/refresh-token")
    public ResponseEntity <?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.refreshToken(request));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
        
    }
    

}
