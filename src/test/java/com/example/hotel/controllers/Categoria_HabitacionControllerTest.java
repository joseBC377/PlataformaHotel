package com.example.hotel.controllers;

import com.example.hotel.Controllers.Categoria_HabitacionRestController;
import com.example.hotel.Services.Categoria_HabitacionService;
import com.example.hotel.entities.Categoria_Habitacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Categoria_HabitacionRestController.class)
public class Categoria_HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    private Categoria_HabitacionService service;

    private Categoria_Habitacion categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria_Habitacion();
        categoria.setId(1);
        categoria.setNombre("Suite Deluxe");
        categoria.setDescripcion("Habitación amplia con jacuzzi");
    }

    @Test
    void testFindAllCategorias() throws Exception {
        when(service.selCategoria_Habitacions()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/api/categoria_habitacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetCategoriaById_Existente() throws Exception {
        when(service.getCategoriaById(1)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categoria_habitacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetCategoriaById_NoExistente() throws Exception {
        when(service.getCategoriaById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categoria_habitacion/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsertCategoria() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.insertCategoria(any(Categoria_Habitacion.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/categoria_habitacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateCategoria_Existente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.updateCategoria(any(), any())).thenReturn(Optional.of(categoria));

        mockMvc.perform(put("/api/categoria_habitacion/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateCategoria_NoExistente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.updateCategoria(any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categoria_habitacion/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategoria_Existente() throws Exception {
        when(service.deleteCategoria(1)).thenReturn(true);

        mockMvc.perform(delete("/api/categoria_habitacion/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategoria_NoExistente() throws Exception {
        when(service.deleteCategoria(999)).thenReturn(false);

        mockMvc.perform(delete("/api/categoria_habitacion/999"))
                .andExpect(status().isNotFound());
    }
}
