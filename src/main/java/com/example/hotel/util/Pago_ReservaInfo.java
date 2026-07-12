package com.example.hotel.util;

import java.math.BigDecimal;
import java.time.LocalDate; // Importa LocalDate

public record Pago_ReservaInfo(
    // Pago
    BigDecimal total,
    String metodo_pago,
    String estado_pago,

    // Reserva
    Integer id_usuario,
    Integer id_habitacion,     
    LocalDate fecha_inicio,   
    LocalDate fecha_fin        
) {
}