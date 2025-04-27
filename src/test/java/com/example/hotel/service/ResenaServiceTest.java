package com.example.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.entities.Resena;
import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.ResenaRepository;
import com.example.hotel.services.ResenaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    private Resena resena;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Usuario usuario= new Usuario();
        Habitacion habitacion= new Habitacion();
        resena = new Resena(1, 5, LocalDate.now(), usuario, habitacion);
    }

    @Test
    void testSelectAllResenas() {
        when(resenaRepository.findAll()).thenReturn(List.of(resena));

        List<Resena> result = resenaService.selectAllResenas();

        assertEquals(1, result.size());
        verify(resenaRepository).findAll();
    }

    @Test
    void testSelectById_Existente() {
        when(resenaRepository.findById(1)).thenReturn(Optional.of(resena));

        Optional<Resena> result = resenaService.getResenaById(1);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getCalificacion());
    }

    @Test
    void testSelectById_NoExistente() {
        when(resenaRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Resena> result = resenaService.getResenaById(999);

        assertFalse(result.isPresent());
    }

    @Test
    void testInsUpdResena() {
        when(resenaRepository.save(resena)).thenReturn(resena);

        Resena result = resenaService.insertResena(resena);

        assertNotNull(result);
        assertEquals(5, result.getCalificacion());
    }

    @Test
    void testDeleteExistente() {
        when(resenaRepository.existsById(1)).thenReturn(true);

        boolean result = resenaService.deleteResena(1);

        assertTrue(result);
        verify(resenaRepository).deleteById(1);
    }

    @Test
    void testDeleteNoExistente() {
        when(resenaRepository.existsById(999)).thenReturn(false);

        boolean result = resenaService.deleteResena(999);

        assertFalse(result);
        verify(resenaRepository, never()).deleteById(any());
    }
}