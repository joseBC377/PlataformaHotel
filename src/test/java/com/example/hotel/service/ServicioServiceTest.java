// package com.example.hotel.service;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.example.hotel.entities.Servicio;
// import com.example.hotel.repositories.ServicioRepository;
// import com.example.hotel.Services.ServicioService;

// public class ServicioServiceTest {

//     @Mock
//     private ServicioRepository servicioRepository;

//     @InjectMocks
//     private ServicioService servicioService;

//     private Servicio servicio;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         servicio = new Servicio();
//         servicio.setId(1);
//         servicio.setNombre("Spa");
//         servicio.setDescripcion("Relajación total");
//         servicio.setPrecio(50.0);
//         servicio.setImagen("spa.jpg");
//     }

//     @Test
//     void testSelectAllServicio() {
//         List<Servicio> servicios = List.of(servicio);
//         when(servicioRepository.findAll()).thenReturn(servicios);

//         List<Servicio> result = servicioService.selectAllServicio();

//         assertEquals(1, result.size());
//         verify(servicioRepository, times(1)).findAll();
//     }

//     @Test
//     void testSelectById_Existente() {
//         when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));

//         Optional<Servicio> result = servicioService.selectById(1);

//         assertTrue(result.isPresent());
//         assertEquals(1, result.get().getId());
//         verify(servicioRepository).findById(1);
//     }

//     @Test
//     void testSelectById_NoExistente() {
//         when(servicioRepository.findById(2)).thenReturn(Optional.empty());

//         Optional<Servicio> result = servicioService.selectById(2);

//         assertFalse(result.isPresent());
//     }

//     @Test
//     void testInsUpdServicio() {
//         when(servicioRepository.save(servicio)).thenReturn(servicio);

//         Servicio result = servicioService.insUpdServicio(servicio);

//         assertNotNull(result);
//         assertEquals(1, result.getId());
//         verify(servicioRepository).save(servicio);
//     }

//     @Test
//     void testDeleteExistente() {
//         when(servicioRepository.existsById(1)).thenReturn(true);

//         boolean eliminado = servicioService.delete(1);

//         assertTrue(eliminado);
//         verify(servicioRepository).deleteById(1);
//     }

//     @Test
//     void testDeleteNoExistente() {
//         when(servicioRepository.existsById(999)).thenReturn(false);

//         boolean eliminado = servicioService.delete(999);

//         assertFalse(eliminado);
//         verify(servicioRepository, never()).deleteById(any());
//     }
// }  
