package com.example.hotel.services;

import org.springframework.stereotype.Service;

import com.example.hotel.config.JwtConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

}
