package com.example.hotel.controllers;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.services.CategoriaHabitacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(Categoria_HabitacionRestController.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CategoriaHabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean

    private CategoriaHabitacionService service;

    private CategoriaHabitacion categoria;

    @BeforeEach
    void setUp() {
        categoria = new CategoriaHabitacion();
        categoria.setId(1);
        categoria.setNombre("Suite Deluxe");
        categoria.setDescripcion("Habitación amplia con jacuzzi");
    }

    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    void testFindAllCategorias() throws Exception {
        when(service.selCategoriaHabitacions()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/api/categoriaHabitacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetCategoriaById_Existente() throws Exception {
        when(service.getCategoriaById(1)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categoriaHabitacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetCategoriaById_NoExistente() throws Exception {
        when(service.getCategoriaById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categoriaHabitacion/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsertCategoria() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.insertCategoria(any(CategoriaHabitacion.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/categoriaHabitacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateCategoria_Existente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.updateCategoria(any(), any())).thenReturn(Optional.of(categoria));

        mockMvc.perform(put("/api/categoriaHabitacion/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateCategoria_NoExistente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(service.updateCategoria(any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categoriaHabitacion/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategoria_Existente() throws Exception {
        when(service.deleteCategoria(1)).thenReturn(true);

        mockMvc.perform(delete("/api/categoriaHabitacion/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategoria_NoExistente() throws Exception {
        when(service.deleteCategoria(999)).thenReturn(false);

        mockMvc.perform(delete("/api/categoriaHabitacion/999"))
                .andExpect(status().isNotFound());
    }

}
