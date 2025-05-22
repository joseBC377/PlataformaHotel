package com.example.hotel.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.hotel.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims,
        UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtConfig.getTokenExpirationInMillis());
    }


    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(), userDetails, jwtConfig.getRefreshTokenExpirationInMillis());
    }


    private String buildToken(
        Map <String , Object> extraClaims,
        UserDetails userDetails,
        long expirationMillis //Milisegundos
    ) {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expirationMillis))
            .signWith(secretKey)
            .compact();
    }



   

    /*
    private String buildToken(){
        
    }
    */







}
