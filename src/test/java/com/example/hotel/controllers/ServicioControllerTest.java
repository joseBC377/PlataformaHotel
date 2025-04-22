// package com.example.hotel.controllers;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.example.hotel.entities.Servicio;
// import com.example.hotel.Services.ServicioService;
// import com.example.hotel.Controllers.ServicioRestController;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(ServicioRestController.class)
// public class ServicioControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockitoBean
//     private ServicioService service;

//     private Servicio servicio;

//     @BeforeEach
//     void setUp() {
//         servicio = new Servicio();
//         servicio.setId(1);
//         servicio.setNombre("Spa");
//         servicio.setDescripcion("Relajación total");
//         servicio.setPrecio(50.0);
//         servicio.setImagen("spa.jpg");
//     }

//     @Test
//     void testSelectAllServicios() throws Exception {
//         when(service.selectAllServicio()).thenReturn(List.of(servicio));

//         mockMvc.perform(get("/api/servicios"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1));
//     }

//     @Test
//     void testGetServicioById_Existente() throws Exception {
//         when(service.selectById(1)).thenReturn(Optional.of(servicio));

//         mockMvc.perform(get("/api/servicios/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1));
//     }

//     @Test
//     void testGetServicioById_NoExistente() throws Exception {
//         when(service.selectById(99)).thenReturn(Optional.empty());

//         mockMvc.perform(get("/api/servicios/99"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testInsUpdServicio() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();
//         when(service.insUpdServicio(any(Servicio.class))).thenReturn(servicio);

//         mockMvc.perform(post("/api/servicios")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(servicio)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1));
//     }

//     @Test
//     void testActualizarServicio_Existente() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();
//         when(service.selectById(1)).thenReturn(Optional.of(servicio));
//         when(service.insUpdServicio(any(Servicio.class))).thenReturn(servicio);

//         mockMvc.perform(put("/api/servicios/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(servicio)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1));
//     }

//     @Test
//     void testActualizarServicio_NoExistente() throws Exception {
//         ObjectMapper mapper = new ObjectMapper();
//         when(service.selectById(999)).thenReturn(Optional.empty());

//         mockMvc.perform(put("/api/servicios/999")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(servicio)))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void testDeleteServicio_Existente() throws Exception {
//         when(service.delete(1)).thenReturn(true);

//         mockMvc.perform(delete("/api/servicios/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("Servicio eliminado correctamente."));
//     }

//     @Test
//     void testDeleteServicio_NoExistente() throws Exception {
//         when(service.delete(999)).thenReturn(false);

//         mockMvc.perform(delete("/api/servicios/999"))
//                 .andExpect(status().isNotFound())
//                 .andExpect(content().string("Servicio no encontrado."));
//     }
// }
