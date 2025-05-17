package com.example.hotel.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Pago_ReservaInfo(

    //Pago
    BigDecimal total,
    String metodo_pago,
    String estado_pago,
    //LocalDateTime fecha_pago,

    //Reserva
    LocalDateTime fecha_inicio,
    LocalDateTime fecha_fin,
    Integer id_usuario



) {

}
