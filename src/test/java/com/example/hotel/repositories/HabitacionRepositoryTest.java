package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel.entities.Habitacion;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class HabitacionRepositoryTest {

    @Autowired
    private HabitacionRepository repository;

    @Test
    public void insertarHabitacion() {
        Habitacion habitacion = new Habitacion();
        habitacion.setNombre("Suite Deluxe");
        habitacion.setDescripcion("Habitación con vista al mar y jacuzzi.");
        habitacion.setEstado("Disponible");

        Habitacion habitacionGuardada = repository.save(habitacion);

        // Verificar que no sea null y que tenga ID generado
        assertNotNull(habitacionGuardada.getId());
        assertEquals("Suite Deluxe", habitacionGuardada.getNombre());
        assertEquals("Disponible", habitacionGuardada.getEstado());
    }
}
