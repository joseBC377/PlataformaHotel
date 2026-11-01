package com.example.hotel.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        // 1. Peticiones de pre-vuelo del navegador (CORS preflight)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Autenticación pública
                        .requestMatchers("/api/v1/auth/autenticarse", "/api/v1/auth/registro").permitAll()
                        .requestMatchers("/api/v1/auth/editar/**").hasAnyAuthority("ADMIN", "RECEPCION")

                        // 3. Catálogo público (GET): soporte tanto en singular como en plural
                        .requestMatchers(HttpMethod.GET,
                                "/api/resena/**",
                                "/api/resenas/**",
                                "/api/habitacion/**",
                                "/api/habitaciones/**",
                                "/api/servicio/**",
                                "/api/servicios/**").permitAll()

                        // 4. Reseñas: operaciones de escritura requieren autenticación
                        .requestMatchers("/api/resena/**", "/api/resenas/**").authenticated()

                        // 5. Modificaciones de habitaciones y servicios (POST, PUT, DELETE)
                        .requestMatchers(
                                "/api/habitacion/**",
                                "/api/habitaciones/**",
                                "/api/servicio/**",
                                "/api/servicios/**")
                                .hasAnyAuthority("ADMIN", "RECEPCION")

                        // 6. Usuarios
                        .requestMatchers("/api/usuario/**", "/api/usuarios/**")
                                .hasAnyAuthority("ADMIN", "RECEPCION")

                        // 7. Todo lo demás (reservas, pagos, historial, etc.)
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://127.0.0.1:4200",
                "http://localhost:5173",
                "http://127.0.0.1:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}