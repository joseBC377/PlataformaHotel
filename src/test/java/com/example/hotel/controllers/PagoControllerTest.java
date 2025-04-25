package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.services.PagoService;

@WebMvcTest(PagoController.class) //Cargo solo el controlador PagoController para hacer pruebas
@AutoConfigureMockMvc //con esto me permite simular solicitudes http (get,post, put, delete)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Permite probar mis controladores , sin necesidad de levantar un servidor real es decir simulamos solicitures http  (GET,POST,PUT,DELETE)

    @MockBean
    private PagoService service;

    @Test
    public void insertIdPago() throws Exception {

        // Simular reserva
        Reserva reserva = new Reserva();
        reserva.setId(1);

        //ESTO ES COMO EL BACKEND, RECIBE LO DE jsonContent la linea 53 hasta la 60 , lo procesa en backend es decir ese json enviado lo transforma en un objeto por eso debe coincidir
        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setTotal(new BigDecimal(125.4));
        pago.setMetodo_pago("Efectivo");
        pago.setEstado_pago("Pagado");
        pago.setFecha_pago(LocalDateTime.of(2025, 4, 24, 10, 0));
        pago.setReserva(reserva);

        when(service.insert(any(Pago.class))).thenReturn(pago); //“Cuando alguien llame a service.insert(...) con cualquier objeto Pago, entonces devuélvele este objeto pago que acabo de crear.”

        // ESTE ES EL CUERPO DE LA SOLICITUD HTTP TIPO POST , SIMULA COMO SI ESTO ES ENVIADO DESDE UN FRONTED (lo importante es que la estructura del formato json coincida con la estructura del objeto java)
        String jsonContent = """
            {
                "total": 125.4,
                "metodo_pago": "Efectivo",
                "estado_pago": "Pagado",
                "fecha_pago": "2025-04-24T10:00:00",
                "reserva": { "id": 1 }
            }""";

        // indica que esto debe estar en la respuesta  es decir VALIDA si esto esta en el objeto
        mockMvc.perform(MockMvcRequestBuilders.post("/api/pago/insertar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(125.4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metodo_pago").value("Efectivo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado_pago").value("Pagado"));
    }



}
