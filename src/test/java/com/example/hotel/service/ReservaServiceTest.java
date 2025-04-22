package com.example.hotel.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotel.entities.Reserva;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.Services.ReservaService;

public class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reserva = new Reserva();
        reserva.setId(1);
        reserva.setFecha_inicio(LocalDateTime.now().plusDays(1));
        reserva.setFecha_fin(LocalDateTime.now().plusDays(2));
    }

    @Test
    void testSelectAllReserva() {
        List<Reserva> reservas = List.of(reserva);
        when(reservaRepository.findAll()).thenReturn(reservas);

        List<Reserva> result = reservaService.selectAllReserva();

        assertEquals(1, result.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void testSelectById_Existente() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        Optional<Reserva> result = reservaService.selectById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(reservaRepository).findById(1);
    }

    @Test
    void testSelectById_NoExistente() {
        when(reservaRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Reserva> result = reservaService.selectById(2);

        assertFalse(result.isPresent());
    }

    @Test
    void testInsUpdReserva() {
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva result = reservaService.insUpdReserva(reserva);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testDeleteExistente() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        boolean eliminado = reservaService.delete(1);

        assertTrue(eliminado);
        verify(reservaRepository).deleteById(1);
    }

    @Test
    void testDeleteNoExistente() {
        when(reservaRepository.existsById(999)).thenReturn(false);

        boolean eliminado = reservaService.delete(999);

        assertFalse(eliminado);
        verify(reservaRepository, never()).deleteById(any());
    }
}
