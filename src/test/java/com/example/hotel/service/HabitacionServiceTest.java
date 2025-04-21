package com.example.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import com.example.hotel.Services.HabitacionService;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.repositories.HabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private HabitacionService habitacionService;

    private Habitacion habitacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        habitacion = new Habitacion();
        habitacion.setId(1);
        habitacion.setNombre("Suite Junior");
        habitacion.setDescripcion("Habitación amplia con cama queen y baño privado.");
        habitacion.setEstado("Disponible");
    }

    @Test
    void testSelectAllHabitacions() {
        List<Habitacion> lista = List.of(habitacion);
        when(habitacionRepository.findAll()).thenReturn(lista);

        List<Habitacion> result = habitacionService.selectAllHabitacions();

        assertEquals(1, result.size());
        verify(habitacionRepository).findAll();
    }

    @Test
    void testGetHabitacionById_Existente() {
        when(habitacionRepository.findById(1)).thenReturn(Optional.of(habitacion));

        Optional<Habitacion> result = habitacionService.getHabitacionById(1);

        assertTrue(result.isPresent());
        assertEquals("Suite Junior", result.get().getNombre());
        verify(habitacionRepository).findById(1);
    }

    @Test
    void testGetHabitacionById_NoExistente() {
        when(habitacionRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Habitacion> result = habitacionService.getHabitacionById(2);

        assertFalse(result.isPresent());
    }

    @Test
    void testInsertHabitacion() {
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);

        Habitacion result = habitacionService.insertHabitacion(habitacion);

        assertNotNull(result);
        assertEquals("Suite Junior", result.getNombre());
        verify(habitacionRepository).save(habitacion);
    }

    @Test
    void testUpdateHabitacion_Existente() {
        Habitacion actualizada = new Habitacion();
        actualizada.setNombre("Suite Ejecutiva");
        actualizada.setDescripcion("Más cómoda y moderna.");
        actualizada.setEstado("Ocupado");

        when(habitacionRepository.findById(1)).thenReturn(Optional.of(habitacion));
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(actualizada);

        Optional<Habitacion> result = habitacionService.updateHabitacion(1, actualizada);

        assertTrue(result.isPresent());
        assertEquals("Suite Ejecutiva", result.get().getNombre());
        verify(habitacionRepository).save(any(Habitacion.class));
    }

    @Test
    void testUpdateHabitacion_NoExistente() {
        Habitacion nueva = new Habitacion();
        nueva.setNombre("Nueva");
        nueva.setDescripcion("Desc");
        nueva.setEstado("Disponible");

        when(habitacionRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Habitacion> result = habitacionService.updateHabitacion(999, nueva);

        assertFalse(result.isPresent());
        verify(habitacionRepository, never()).save(any());
    }

    @Test
    void testDeleteHabitacion_Existente() {
        when(habitacionRepository.existsById(1)).thenReturn(true);

        boolean eliminado = habitacionService.deleteHabitacion(1);

        assertTrue(eliminado);
        verify(habitacionRepository).deleteById(1);
    }

    @Test
    void testDeleteHabitacion_NoExistente() {
        when(habitacionRepository.existsById(404)).thenReturn(false);

        boolean eliminado = habitacionService.deleteHabitacion(404);

        assertFalse(eliminado);
        verify(habitacionRepository, never()).deleteById(any());
    }
}
