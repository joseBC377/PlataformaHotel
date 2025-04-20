package com.example.hotel;

import com.example.hotel.api.Categoria_HabitacionRestController;
import com.example.hotel.entities.Categoria_Habitacion;
import com.example.hotel.services.Categoria_HabitacionService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(Categoria_HabitacionRestController.class)
public class Categoria_HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Categoria_HabitacionService categoriaHabitacionService;
    @Test
    void shouldFindAllCategorias() throws Exception {
        List<Categoria_Habitacion> categorias = List.of(
            new Categoria_Habitacion(), 
            new Categoria_Habitacion(), 
            new Categoria_Habitacion()
        );
        Mockito.when(categoriaHabitacionService.selCategoria_Habitacions()).thenReturn(categorias);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categoria_habitacion"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }
}
