package com.example.hotel.controllers;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.services.HabitacionService;
import com.example.hotel.services.JwtService;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo; 
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = HabitacionRestController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {HabitacionRestController.class})
public class HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HabitacionService service;

    @MockitoBean
    private JwtService jwtService;

    private Habitacion habitacion;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        habitacion = new Habitacion();
        habitacion.setId_habitacion(1);
        habitacion.setNombre_habitacion("Habitación 101");
        habitacion.setDescripcion_habitacion("Habitación con cama doble y vista al jardín");
        habitacion.setEstado(RolHabitacion.DISPONIBLE);
        habitacion.setTipo(RolTipo.LIMPIO); 
    }

    @Test
    void testSelectAllHabitacions() throws Exception {
        when(service.selectAllHabitacions()).thenReturn(List.of(habitacion));

        mockMvc.perform(get("/api/habitacion")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_habitacion").value(1));
    }

    @Test
    void testGetHabitacionById_Existente() throws Exception {
        when(service.getHabitacionById(1)).thenReturn(Optional.of(habitacion));

        mockMvc.perform(get("/api/habitacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_habitacion").value(1));
    }

    @Test
    void testGetHabitacionById_NoExistente() throws Exception {
        when(service.getHabitacionById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/habitacion/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsertHabitacion() throws Exception {
        when(service.insertHabitacion(any(Habitacion.class))).thenReturn(habitacion);

        mockMvc.perform(post("/api/habitacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(habitacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_habitacion").value(1));
    }

    @Test
    void testUpdateHabitacion_NoExistente() throws Exception {
        when(service.updateHabitacion(any(Integer.class), any(Habitacion.class))).thenReturn(Optional.empty());

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