package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Optional;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.services.HabitacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(HabitacionRestController.class)
public class HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HabitacionService service;

    private Habitacion habitacion;

    @BeforeEach
    void setUp() {
        habitacion = new Habitacion();
        habitacion.setId(1);
        habitacion.setNombre("Habitación 101");
        habitacion.setDescripcion("Habitación con cama doble y vista al jardín");
        habitacion.setEstado("Disponible");
    }

    @Test
    void testSelectAllHabitacions() throws Exception {
        when(service.selectAllHabitacions()).thenReturn(List.of(habitacion));

        mockMvc.perform(get("/api/habitacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetHabitacionById_Existente() throws Exception {
        when(service.getHabitacionById(1)).thenReturn(Optional.of(habitacion));

        mockMvc.perform(get("/api/habitacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetHabitacionById_NoExistente() throws Exception {
        when(service.getHabitacionById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/habitacion/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsertHabitacion() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        when(service.insertHabitacion(any(Habitacion.class))).thenReturn(habitacion);

        mockMvc.perform(post("/api/habitacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(habitacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateHabitacion_Existente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        when(service.updateHabitacion(1, habitacion)).thenReturn(Optional.of(habitacion));

        mockMvc.perform(put("/api/habitacion/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(habitacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateHabitacion_NoExistente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        when(service.updateHabitacion(999, habitacion)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/habitacion/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(habitacion)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteHabitacion_Existente() throws Exception {
        when(service.deleteHabitacion(1)).thenReturn(true);

        mockMvc.perform(delete("/api/habitacion/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteHabitacion_NoExistente() throws Exception {
        when(service.deleteHabitacion(999)).thenReturn(false);

        mockMvc.perform(delete("/api/habitacion/999"))
                .andExpect(status().isNotFound());
    }
}
