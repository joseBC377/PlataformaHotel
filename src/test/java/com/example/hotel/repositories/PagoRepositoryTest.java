package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.annotation.Rollback;
import com.example.hotel.util.RolEstadoPago;
import com.example.hotel.util.RolPago;
import com.example.hotel.HotelApplication;
import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;

import jakarta.transaction.Transactional;

// @ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
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
        reserva.setFecha_reserva(null);

        Pago pago = new Pago();
        pago.setTotal(new BigDecimal(125.4));
         pago.setEstado(RolEstadoPago.RECHAZADO);
       pago.setFecha_pago(LocalDate.now());
        pago.setReserva(reserva);

        Pago pagoGuardar = repository.save(pago);

        assertNotNull(pagoGuardar.getIdPago());
        assertEquals(RolEstadoPago.RECHAZADO, pagoGuardar.getEstado());

    }

}
