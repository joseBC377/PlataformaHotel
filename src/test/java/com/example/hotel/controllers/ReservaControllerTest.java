package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.Usuario;
import com.example.hotel.services.ReservaService;
import com.example.hotel.util.RolReserva;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(controllers = ReservaRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {ReservaRestController.class})
@AutoConfigureMockMvc(addFilters = false) 
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService service;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId_reserva(1);
        reserva.setFechaCreacion(LocalDate.now()); 
        reserva.setEstado(RolReserva.PENDIENTE);   
        
        Usuario usuario = new Usuario();         
        usuario.setId_usuario(1);
        reserva.setUsuario(usuario);
    }

    @Test
    void testSelectAllReservas() throws Exception {
        when(service.selectAllReserva()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_reserva").value(1));
    }

    @Test
    void testGetReservaById_Existente() throws Exception {
        when(service.selectById(1)).thenReturn(Optional.of(reserva));

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_reserva").value(1));
    }

    @Test
    void testInsUpdReserva() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        when(service.insUpdReserva(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_reserva").value(1));
    }

    @Test
    void testActualizar_Existente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Mockeamos los servicios
        when(service.selectById(1)).thenReturn(Optional.of(reserva));
        when(service.insUpdReserva(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(put("/api/reservas/1") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reserva))) // Aquí asegúrate de que el objeto esté completo
                .andDo(print()) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_reserva").value(1));
    }
}