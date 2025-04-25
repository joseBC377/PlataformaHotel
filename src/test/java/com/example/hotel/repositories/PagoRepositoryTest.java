package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class PagoRepositoryTest {

    @Autowired
    private PagoRepository repository;

    @Test
    public void insertarPago() {

        // Simular reserva
        Reserva reserva = new Reserva();
        reserva.setId(1);

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
