//import org.springframework.boot.test.context.SpringBootTest;
//import com.example.hotel.HotelApplication;
package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.util.RolHabitacion;

import jakarta.transaction.Transactional;


// @ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)

@Transactional
public class HabitacionRepositoryTest {

    @Autowired
    private HabitacionRepository repository;

    @Test
    public void insertarHabitacion() {
        Habitacion habitacion = new Habitacion();
        habitacion.setNombre_habitacion("Suite Deluxe");
        habitacion.setDescripcion_habitacion("Habitación con vista al mar y jacuzzi.");
        habitacion.setEstado(RolHabitacion.DISPONIBLE); // Asignar un valor válido según tu enum RolHabitacion

        Habitacion habitacionGuardada = repository.save(habitacion);

        // Verificar que no sea null y que tenga ID generado
        assertNotNull(habitacionGuardada.getId_habitacion());
        assertEquals("Suite Deluxe", habitacionGuardada.getNombre_habitacion());
        assertEquals("Disponible", habitacionGuardada.getEstado().toString());
    }
}
