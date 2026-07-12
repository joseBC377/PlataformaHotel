package com.example.hotel.controllers;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.services.CategoriaHabitacionService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = CategoriaHabitacionRestController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {CategoriaHabitacionRestController.class}) 
class CategoriaHabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaHabitacionService service;

    private CategoriaHabitacion categoria;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        categoria = new CategoriaHabitacion();
        categoria.setId_categoria_habitacion(1);
        categoria.setNombre_categoria("Suite Deluxe");
        categoria.setDescripcion_categoria("Habitación amplia con jacuzzi");
        categoria.setCapacidad(2); 
        categoria.setPrecio(new BigDecimal("100.00")); 
    }

    @Test
    void testFindAllCategorias() throws Exception {
        when(service.selCategoriaHabitacions()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/api/categoriaHabitacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_categoria_habitacion").value(1));
    }

    @Test
    void testGetCategoriaById_Existente() throws Exception {
        when(service.getCategoriaById(1)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categoriaHabitacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_categoria_habitacion").value(1));
    }

    @Test
    void testInsertCategoria() throws Exception {
        when(service.insertCategoria(any(CategoriaHabitacion.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/categoriaHabitacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_categoria_habitacion").value(1));
    }

    @Test
    void testDeleteCategoria_Existente() throws Exception {
        when(service.deleteCategoria(1)).thenReturn(true);

        mockMvc.perform(delete("/api/categoriaHabitacion/1"))
                .andExpect(status().isNoContent());
    }
}