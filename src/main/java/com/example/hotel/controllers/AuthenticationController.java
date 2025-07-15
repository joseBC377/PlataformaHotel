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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Se podria decir que con este controlador generamos token de acceso y refrezco
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registro")
    public void registrar(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarUsuario(@PathVariable Integer id, @RequestBody RegisterRequest request) {
        String mensaje = authenticationService.editarUsuario(id, request);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/autenticarse")
    public ResponseEntity<AuthenticationResponse> authenticar(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    // Con este solicitamos con nuestro token refresh un nuevo token de access
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.refreshToken(request));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }

    }

}
