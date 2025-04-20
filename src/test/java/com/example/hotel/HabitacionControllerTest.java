package com.example.hotel;

import com.example.hotel.api.HabitacionRestController;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.services.HabitacionService;
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

@WebMvcTest(HabitacionRestController.class)
public class HabitacionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HabitacionService habitacionService;  

    @Test
    void shouldFindAllPhotos() throws Exception {
        List<Habitacion> habitaciones = List.of(
            new Habitacion(), new Habitacion(), new Habitacion()
        );
        Mockito.when(habitacionService.selectAllHabitacions()).thenReturn(habitaciones);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/habitacion"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }
}
