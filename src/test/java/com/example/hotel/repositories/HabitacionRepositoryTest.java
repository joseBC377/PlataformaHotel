//import org.springframework.boot.test.context.SpringBootTest;
//import com.example.hotel.HotelApplication;
package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo;
import java.math.BigDecimal;

// import jakarta.transaction.Transactional;


// @ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)

// @Transactional
@SpringBootTest
public class HabitacionRepositoryTest {

    @Autowired
    private HabitacionRepository repository;

    @Autowired
    private CategoriaHabitacionRepository categoriaRepository;

    @Test
    public void insertarHabitacion() {
        // 1. Crear y guardar primero una categoría en la base de datos de prueba
        CategoriaHabitacion categoria = new CategoriaHabitacion();
        categoria.setNombre_categoria("Matrimonial"); // Ajusta los métodos set según los atributos de tu entidad CategoriaHabitacion
        categoria.setDescripcion_categoria("Habitación para dos personas");
        categoria.setPrecio(BigDecimal.valueOf(150.0));
        categoria.setCapacidad(2);
        categoria = categoriaRepository.save(categoria);

        // 2. Crear la habitación usando la categoría que acabamos de guardar
        Habitacion habitacion = new Habitacion();
        habitacion.setNombre_habitacion("Suite Deluxe");
        habitacion.setDescripcion_habitacion("Habitación con vista al mar y jacuzzi.");
        habitacion.setEstado(RolHabitacion.DISPONIBLE);
        habitacion.setTipo(RolTipo.LIMPIO);
        
        habitacion.setCategoriaHabitacion(categoria); 

        // 3. Guardar y verificar
        Habitacion habitacionGuardada = repository.save(habitacion);

        assertNotNull(habitacionGuardada.getId_habitacion());
        assertEquals("Suite Deluxe", habitacionGuardada.getNombre_habitacion());
    }
}