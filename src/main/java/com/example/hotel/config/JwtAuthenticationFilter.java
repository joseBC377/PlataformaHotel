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
    
    //Encarga de verificar el token JWT en cada peticion HTTP y autenticar al usuario si el token es valido
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
        )
            throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer") ) { //verifica si el encabezado es nulo o mal formado , si no hay token o no empieza con Bearer  no hace nada y deja que siga la solicitud
                filterChain.doFilter(request, response);
                return;
            }
        
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            //Extrae el JWT y el correo(username)
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTookenValid(jwt, userDetails)) { //Comprueba que el usuario no este autenticado ,para autenticarlo
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                
            }
        filterChain.doFilter(request, response);
    }

    


}
