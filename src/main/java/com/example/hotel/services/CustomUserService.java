package com.example.hotel.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hotel.entities.CustomUser;
import com.example.hotel.entities.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService{


    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario;
        try {
            usuario = service.findByCorreo(username);
            return new CustomUser(usuario);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }

}
