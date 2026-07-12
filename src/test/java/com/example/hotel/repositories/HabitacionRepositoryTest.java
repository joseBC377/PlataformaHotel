package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Transactional
public class HabitacionRepositoryTest {

    @Autowired
    private HabitacionRepository repository;

    @Autowired
    private CategoriaHabitacionRepository categoriaRepository;

    @BeforeEach
    public void setup() {
        // Limpiamos las tablas antes de cada test para asegurar un estado fresco
        repository.deleteAll();
        categoriaRepository.deleteAll();
    }

   @Test
    public void insertarHabitacion() {
        // 1. Crear nombre único para la Categoría
        String nombreCategoria = "Matrimonial_" + UUID.randomUUID().toString();
        CategoriaHabitacion categoria = new CategoriaHabitacion();
        categoria.setNombre_categoria(nombreCategoria);
        categoria.setDescripcion_categoria("Habitación para dos personas");
        categoria.setPrecio(BigDecimal.valueOf(150.0));
        categoria.setCapacidad(2);
        categoria = categoriaRepository.save(categoria);

        // 2. Crear nombre único para la Habitación
        String nombreHabitacion = "Suite Deluxe_" + UUID.randomUUID().toString();
        Habitacion habitacion = new Habitacion();
        habitacion.setNombre_habitacion(nombreHabitacion); // <--- NOMBRE ÚNICO AQUÍ
        habitacion.setDescripcion_habitacion("Habitación con vista al mar y jacuzzi.");
        habitacion.setEstado(RolHabitacion.DISPONIBLE);
        habitacion.setTipo(RolTipo.LIMPIO);
        habitacion.setCategoriaHabitacion(categoria); 

        // 3. Guardar
        Habitacion habitacionGuardada = repository.save(habitacion);

        assertNotNull(habitacionGuardada.getId_habitacion());
        assertEquals(nombreHabitacion, habitacionGuardada.getNombre_habitacion());
    }
}