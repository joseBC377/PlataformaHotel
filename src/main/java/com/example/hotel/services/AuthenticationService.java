package com.example.hotel.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hotel.entities.CustomUser;
import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.util.AuthenticationRequest;
import com.example.hotel.util.AuthenticationResponse;
import com.example.hotel.util.RefreshTokenRequest;
import com.example.hotel.util.RegisterRequest;
import com.example.hotel.util.Rol;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    // EL AuthenticationResponse VIENE DE util, AuthenticationResponse

    // El RegisterRequest viene del paquete util , RegisterRequest
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Usuario.builder()
                .nombre(request.firstname())
                .apellido(request.lastname())
                .correo(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(Rol.CLIENT)
                .build();
        usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    // El AuthenticationRequest viene del paquete util
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = usuarioRepository.findByCorreo(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(jwtToken, refreshToken);

    }

    // El RefreshTokenRequest viene del paquete util ,
    // proceso de renovacion del access token usando el refresh token
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userEmail = jwtService.extractUsername(request.refreshToken());
        if (userEmail != null) {
            var user = usuarioRepository.findByCorreo(userEmail).orElseThrow();
            if (jwtService.isTookenValid(request.refreshToken(), user)) {
                var accessToken = jwtService.generateToken(user);
                return new AuthenticationResponse(accessToken, request.refreshToken());
            }
        }
        throw new RuntimeException("Token de refrezco invalido");
    }

}
