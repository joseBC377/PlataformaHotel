package com.example.hotel.util;

public record RegisterRequest(

    String firstname,
    String lastname,
    String email,
    String password

) {

}
