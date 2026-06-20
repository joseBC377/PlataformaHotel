package com.example.hotel.util;

import java.time.LocalDate;

public record RegisterRequest(

    String nombre_usuario,
    String apellido_paterno,
    String apellido_materno,
    String correo,
    String telefono,
    LocalDate fecha_nacimiento,
    String password,
    Rol rol

) {

}
