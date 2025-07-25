package com.example.hotel.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hotel.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Intercepta cada peticion y verifica si el token JWT es valido , si es valido autentica al usuario para que pueda acceder a rutas protegidas
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException { 

        final String authHeader = request.getHeader("Authorization"); //El Authorization y Bearer es lo que esta en el postman
        if (authHeader == null || !authHeader.startsWith("Bearer")) { // verifica si el encabezado es nulo o mal formado si no hay token o no empieza con Bearer no hace nada y deja que siga la solicitud
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        // Extrae el JWT y el correo(username)
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTookenValid(jwt, userDetails)) { //Aca esta validando el token si es valido, si expiro , el usuario porque ese isTookenValid viene de jwtService
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities()); //esta linea representa el usuario ya autenticado
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);//Autenticamos al usuario en spring security
            }

        }
        filterChain.doFilter(request, response);
    }

}
