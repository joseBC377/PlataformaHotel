package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback

public class PagoRepositoryTest {

    @Autowired
    private PagoRepository repository;
    @Autowired
    private ReservaRepository repository2;

    @Test
    public void insertarPago() {

        // Simular reserva
        Reserva reserva = new Reserva();
        // reserva.setId(1);
        // Asignar fechas a la reserva
        LocalDateTime fechaInicio= LocalDateTime.of(2026, 4, 24, 10, 0, 0);
        reserva.setFecha_inicio(fechaInicio);
        LocalDateTime fechaFin= LocalDateTime.of(2026, 6, 24, 10, 0, 0);
        reserva.setFecha_fin(fechaFin);
        reserva = repository2.save(reserva);

        Pago pago = new Pago();
        pago.setTotal(new BigDecimal(125.4));
        pago.setMetodo_pago("Efectivo");
        pago.setEstado_pago("Pagado");
        pago.setFecha_pago(LocalDateTime.of(2025, 4, 24, 10, 0));
        pago.setReserva(reserva);

        Pago pagoGuardar = repository.save(pago);

        assertNotNull(pagoGuardar.getIdPago());
        assertEquals("Pagado", pagoGuardar.getEstado_pago());

    }

}
