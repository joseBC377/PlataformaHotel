package com.example.hotel.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


//Para la transaccion
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

    public DateTimeFormat fecha_reserva() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fecha_reserva'");
    }

}
