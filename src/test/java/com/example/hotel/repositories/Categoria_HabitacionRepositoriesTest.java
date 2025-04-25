package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel.entities.Categoria_Habitacion;

import jakarta.transaction.Transactional;
/*
@SpringBootTest
@Transactional
public class Categoria_HabitacionRepositoriesTest {

    @Autowired
    private Categoria_HabitacionRepository repository;

    @Test
    public void insertarCategoriaHabitacion() {
        Categoria_Habitacion categoria = new Categoria_Habitacion();
        categoria.setNombre("Familiar");
        categoria.setDescripcion("Ideal para familias grandes con niños.");

        Categoria_Habitacion categoriaGuardada = repository.save(categoria);

        // Verificaciones
        assertNotNull(categoriaGuardada.getId());
        assertEquals("Familiar", categoriaGuardada.getNombre());
        assertEquals("Ideal para familias grandes con niños.", categoriaGuardada.getDescripcion());
    }
}
    */
