package com.example.hotel.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    // encripta la contraseña ingresada para ser comparada con la de la database
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Esta es la parte importante donde hara el proceso de autenticacion
    /*
     * @Bean
     * DaoAuthenticationProvider authenticationProvider() {
     * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
     * provider.setUserDetailsService(service); // Obtiene al usuario
     * provider.setPasswordEncoder(passwordEncoder()); // Compara cotraseñas
     * codificadas
     * return provider;
     * 
     * }
     */

    // @Bean
    // SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
    // return http.authorizeHttpRequests(
    // auth -> auth.requestMatchers("/api/usuario/publico").permitAll() //No pido
    // //autenticacion porque permitAll
    // .requestMatchers("/api/usuario/privado").hasAnyAuthority("ADMIN") //te pide
    // //autenticacion y para que ingreses debes de tener como rol ADMIN
    // .anyRequest().permitAll()) //Cualquier otra ruta tambien libre
    // .httpBasic(Customizer.withDefaults()) //POR DEFECTO EN EL NAVEGADO SALE UN
    // LOGIN BASICO
    // .build();
    // }

    // .formLogin(Customizer.withDefaults())
    // .build();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // Ingresar sin token
                        .requestMatchers("api/usuario/**").hasAuthority("ADMIN")
                        // Cualquier otra ruta no mencionada necesita que el usuario sea autenticado
                        // pero sin importar si es admin o client
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://127.0.0.1:3000", "http://localhost:4200", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
