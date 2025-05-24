package com.example.hotel.services;

import java.util.HashMap;

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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    // EL AuthenticationResponse VIENE DE util, AuthenticationResponse

    // El RegisterRequest viene del paquete util , RegisterRequest (Registrar un usuario y se genera un token (acces y refresh))
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Usuario.builder()
                .nombre(request.firstname())
                .apellido(request.lastname())
                .correo(request.email())
                .telefono(request.telefono())
                .password(passwordEncoder.encode(request.password()))
                .rol(Rol.CLIENT)
                .build();
        usuarioRepository.save(user);

        // ENVOLVEMOS el usuario en un CustomUser (que implementa UserDetails)
        CustomUser customUser = new CustomUser(user);

        //Usamos el metodo correcto con los argumentos esperados
        var jwtToken = jwtService.generateToken(new HashMap<>(),customUser);
        var refreshToken = jwtService.generateRefreshToken(customUser);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    // El AuthenticationRequest viene del paquete util (Al momento de iniciar sesion valida credenciales y se genera un token)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = usuarioRepository.findByCorreo(request.email()).orElseThrow();
        
        CustomUser customUser = new CustomUser(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(),customUser);
        var refreshToken = jwtService.generateRefreshToken(customUser);
        return new AuthenticationResponse(jwtToken, refreshToken);

    }

    // proceso de renovacion del access token usando el refresh token 
    //(es decir cuando se venca el access token , mediante el refresh token se genera un access token y cuando se venca el access token si el refresh token sigo vigente sigue generando un access token hasta que venca el refresh token)
    // El RefreshTokenRequest viene del paquete util ,
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userEmail = jwtService.extractUsername(request.refreshToken());
        if (userEmail != null) {
            var user = usuarioRepository.findByCorreo(userEmail).orElseThrow();

            CustomUser customUser = new CustomUser(user);

            if (jwtService.isTookenValid(request.refreshToken(), customUser)) {
                var accessToken = jwtService.generateToken(new HashMap<>(),customUser);
                return new AuthenticationResponse(accessToken, request.refreshToken());
            }
        }
        throw new RuntimeException("Token de refrezco invalido");
    }

}
