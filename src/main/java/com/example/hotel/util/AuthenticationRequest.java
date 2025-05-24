package com.example.hotel.util;

//Para el usuario iniciar sesion
public record AuthenticationRequest(

    String email,
    String password

) {

}
