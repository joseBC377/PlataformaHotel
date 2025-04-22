// package com.example.hotel.controllers;

// import com.example.hotel.Controllers.ResenaRestController;
// import com.example.hotel.Services.ResenaService;
// import com.example.hotel.entities.Resena;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.web.servlet.MockMvc;

// import java.time.LocalDate;
// import java.util.Optional;
// import java.util.List;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(ResenaRestController.class)
// public class ResenaControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockitoBean
//     private ResenaService resenaService;

//     private Resena resena;

//     @BeforeEach
//     void setUp() {
//         resena = new Resena(1, 5, LocalDate.now(), 1, 1);
//     }

//     @Test
//     void testSelectAllResenas() throws Exception {
//         when(resenaService.selectAllResenas()).thenReturn(List.of(resena));

//         mockMvc.perform(get("/api/resenas"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].calificacion").value(5));
//     }

//     @Test
//     void testGetResenaById_Existente() throws Exception {
//         when(resenaService.selectById(1)).thenReturn(Optional.of(resena));

//         mockMvc.perform(get("/api/resenas/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.calificacion").value(5));
//     }

//     @Test
//     void testGetResenaById_NoExistente() throws Exception {
//         when(resenaService.selectById(999)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/resenas/999"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testCrearResena() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();

//         when(resenaService.insUpdResena(any(Resena.class))).thenReturn(resena);

//         mockMvc.perform(post("/api/resenas")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(resena)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.calificacion").value(5));
//     }

//     @Test
//     void testActualizarResena_Existente() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();

//         when(resenaService.selectById(1)).thenReturn(Optional.of(resena));
//         when(resenaService.insUpdResena(any(Resena.class))).thenReturn(resena);

//         mockMvc.perform(put("/api/resenas/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(resena)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.calificacion").value(5));
//     }

//     @Test
//     void testActualizarResena_NoExistente() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();

//         when(resenaService.selectById(999)).thenReturn(Optional.empty());

//         mockMvc.perform(put("/api/resenas/999")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(resena)))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testEliminarResena_Existente() throws Exception {
//         when(resenaService.delete(1)).thenReturn(true);

//         mockMvc.perform(delete("/api/resenas/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Reseña eliminada correctamente."));
//     }

//     @Test
//     void testEliminarResena_NoExistente() throws Exception {
//         when(resenaService.delete(999)).thenReturn(false);

//         mockMvc.perform(delete("/api/resenas/999"))
//                 .andExpect(status().isNotFound())
//                 .andExpect(content().string("Reseña no encontrada."));
//     }
// }