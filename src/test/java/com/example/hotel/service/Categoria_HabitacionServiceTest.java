package com.example.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.example.hotel.entities.Categoria_Habitacion;
import com.example.hotel.repositories.Categoria_HabitacionRepository;
import com.example.hotel.Services.Categoria_HabitacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Categoria_HabitacionServiceTest {

    @Mock
    private Categoria_HabitacionRepository categoriaRepository;

    @InjectMocks
    private Categoria_HabitacionService categoriaService;

    private Categoria_Habitacion categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria_Habitacion();
        categoria.setId(1);
        categoria.setNombre("Suite");
        categoria.setDescripcion("Habitación de lujo con vista al mar");
        categoria.setCapacidad(4);
        categoria.setPrecio((int) 200.0);
        categoria.setImagen("suite.jpg");
    }

    @Test
    void testSelectAllCategoria() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria_Habitacion> resultado = categoriaService.selCategoria_Habitacions();

        assertEquals(1, resultado.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void testSelectById_Existente() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Optional<Categoria_Habitacion> resultado = categoriaService.getCategoriaById(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        verify(categoriaRepository).findById(1);
    }

    @Test
    void testSelectById_NoExistente() {
        when(categoriaRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Categoria_Habitacion> resultado = categoriaService.getCategoriaById(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testInsertCategoria() {
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria_Habitacion resultado = categoriaService.insertCategoria(categoria);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testUpdateCategoria() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria_Habitacion updatedCategoria = new Categoria_Habitacion();
        updatedCategoria.setNombre("Suite Deluxe");
        updatedCategoria.setDescripcion("Habitación de lujo con vista panorámica");

        Optional<Categoria_Habitacion> resultado = categoriaService.updateCategoria(1, updatedCategoria);

        assertTrue(resultado.isPresent());
        assertEquals("Suite Deluxe", resultado.get().getNombre());
        assertEquals("Habitación de lujo con vista panorámica", resultado.get().getDescripcion());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testDeleteExistente() {
        when(categoriaRepository.existsById(1)).thenReturn(true);

        boolean eliminado = categoriaService.deleteCategoria(1);

        assertTrue(eliminado);
        verify(categoriaRepository).deleteById(1);
    }

    @Test
    void testDeleteNoExistente() {
        when(categoriaRepository.existsById(999)).thenReturn(false);

        boolean eliminado = categoriaService.deleteCategoria(999);

        assertFalse(eliminado);
        verify(categoriaRepository, never()).deleteById(any());
    }
}
