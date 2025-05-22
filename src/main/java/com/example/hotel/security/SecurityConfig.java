package com.example.hotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.hotel.services.CustomUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserService service;

    // encripta la contraseña ingresada para ser comparada con la de la database
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Esta es la parte importante donde hara el proceso de autenticacion
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service); // Obtiene al usuario
        provider.setPasswordEncoder(passwordEncoder()); // Compara cotraseñas codificadas
        return provider;

    }

    // @Bean
    // SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
    // return http.authorizeHttpRequests(
    // auth -> auth.requestMatchers("/api/usuario/publico").permitAll() //No pido
    // //autenticacion porque permitAll
    // .requestMatchers("/api/usuario/privado").hasAnyAuthority("ADMIN") //te pide
    // //autenticacion y para que ingreses debes de tener como rol ADMIN
    // .anyRequest().permitAll()) //Cualquier otra ruta tambien libre
    // .httpBasic(Customizer.withDefaults()) //POR DEFECTO EN EL NAVEGADO SALE UN
    // .build();
    // //LOGIN BASCIO
    //}

    //.formLogin(Customizer.withDefaults())
    //.build();
    

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/usuario/publico").permitAll()
    .requestMatchers("/api/usuario/privado").hasAuthority("ADMIN")
    .anyRequest().authenticated())
    .httpBasic(Customizer.withDefaults())
    .build();
    }

}
