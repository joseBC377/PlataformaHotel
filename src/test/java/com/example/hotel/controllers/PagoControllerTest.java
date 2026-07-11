package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; // IMPORTANTE
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.services.PagoService;
import com.example.hotel.util.RolEstadoPago;

// Añadimos excludeAutoConfiguration para desactivar la seguridad automáticamente
@WebMvcTest(controllers = PagoRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {PagoRestController.class})
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService service;
    
    @MockitoBean
    private com.example.hotel.services.JwtService jwtService;

    @Test
    public void insertIdPago() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(1);

        Pago pago = new Pago();
        pago.setId_pago(1);
        pago.setTotal(new BigDecimal("125.4"));
        pago.setEstado_pago(RolEstadoPago.RECHAZADO);
        pago.setFecha_pago(LocalDate.now());
        pago.setReserva(reserva);
        // Si tu controlador devuelve "Efectivo" por defecto al guardar, asegúrate de setearlo aquí también:
        // pago.setMetodo_pago("Efectivo"); 

        when(service.insert(any(Pago.class))).thenReturn(pago);

        String jsonContent = """
            {
                "total": 125.4,
                "igv": 20.0,
                "estado_pago": "RECHAZADO",
                "fecha_pago": "2026-07-11",
                "reserva": { "id_reserva": 1 },
                "metodoPago": { "id_metodo_pago": 1 }
            }""";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pago/insertar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(125.4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado_pago").value("RECHAZADO"));
    }
}