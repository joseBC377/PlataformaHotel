package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.hotel.entities.Reserva;
import com.example.hotel.Controllers.ReservaRestController;
import com.example.hotel.Services.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservaRestController.class)
public class ReservaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService service;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId(1);
        reserva.setFecha_inicio(LocalDateTime.now().plusDays(1));
        reserva.setFecha_fin(LocalDateTime.now().plusDays(2));
    }

    @Test
    void testSelectAllReservas() throws Exception {
        when(service.selectAllReserva()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetReservaById_Existente() throws Exception {
        when(service.selectById(1)).thenReturn(Optional.of(reserva));

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetReservaById_NoExistente() throws Exception {
        when(service.selectById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsUpdReserva() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        when(service.insUpdReserva(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizar_Existente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        when(service.selectById(1)).thenReturn(Optional.of(reserva));
        when(service.insUpdReserva(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(put("/api/reservas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizar_NoExistente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        when(service.selectById(999)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/reservas/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteReserva_Existente() throws Exception {
        when(service.delete(1)).thenReturn(true);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva eliminada correctamente."));
    }

    @Test
    void testDeleteReserva_NoExistente() throws Exception {
        when(service.delete(999)).thenReturn(false);

        mockMvc.perform(delete("/api/reservas/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reserva no encontrada."));
    }
}