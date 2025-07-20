package PruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.repositories.CategoriaHabitacionRepository;
import com.example.hotel.services.CategoriaHabitacionService;

@ExtendWith(MockitoExtension.class)
public class CategoriaHabitacionServiceTest {

    @Mock
    private CategoriaHabitacionRepository repository;

    @InjectMocks
    private CategoriaHabitacionService service;

    private CategoriaHabitacion categoriaBase;

    @BeforeEach
    void setUp() {
        categoriaBase = CategoriaHabitacion.builder()
            .id(1)
            .nombre("Suite Premium")
            .descripcion("Suite con jacuzzi y vista al mar")
            .capacidad(2)
            .precio(new BigDecimal("400.00"))
            .imagen("imagen.png")
            .build();
    }

    @Test
    @DisplayName("selCategoriaHabitacions: retorna lista con registros")
    void selCategoriaHabitacions_ok() {
        when(repository.findAll()).thenReturn(List.of(categoriaBase));

        List<CategoriaHabitacion> lista = service.selCategoriaHabitacions();

        assertEquals(1, lista.size());
        assertEquals("Suite Premium", lista.get(0).getNombre());
        verify(repository).findAll();
    }


    @Test
    @DisplayName("getCategoriaById: presente")
    void getCategoriaById_presente() {
        when(repository.findById(1)).thenReturn(Optional.of(categoriaBase));

        Optional<CategoriaHabitacion> opt = service.getCategoriaById(1);

        assertTrue(opt.isPresent());
        assertEquals("Suite Premium", opt.get().getNombre());
        verify(repository).findById(1);
    }


    @Test
    @DisplayName("getCategoriaById: vacío cuando no existe")
    void getCategoriaById_vacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Optional<CategoriaHabitacion> opt = service.getCategoriaById(99);

        assertTrue(opt.isEmpty());
        verify(repository).findById(99);
    }


    @Test
    @DisplayName("insertCategoria: guarda y devuelve categoría")
    void insertCategoria_ok() {
        when(repository.save(any(CategoriaHabitacion.class))).thenReturn(categoriaBase);

        CategoriaHabitacion guardada = service.insertCategoria(categoriaBase);

        assertNotNull(guardada);
        assertEquals("Suite Premium", guardada.getNombre());
        assertEquals(0, guardada.getPrecio().compareTo(new BigDecimal("400.00")));

        ArgumentCaptor<CategoriaHabitacion> captor = ArgumentCaptor.forClass(CategoriaHabitacion.class);
        verify(repository).save(captor.capture());
        assertEquals("Suite Premium", captor.getValue().getNombre());
    }


    @Test
    @DisplayName("updateCategoria: actualiza cuando existe")
    void updateCategoria_ok() {
        CategoriaHabitacion cambios = CategoriaHabitacion.builder()
            .nombre("Suite Deluxe")
            .descripcion("Suite Deluxe con terraza")
            .capacidad(3)
            .precio(new BigDecimal("500.00"))
            .imagen("deluxe.png")
            .build();

        when(repository.findById(1)).thenReturn(Optional.of(categoriaBase));
        when(repository.save(any(CategoriaHabitacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<CategoriaHabitacion> opt = service.updateCategoria(1, cambios);

        assertTrue(opt.isPresent());
        CategoriaHabitacion actualizada = opt.get();
        assertEquals("Suite Deluxe", actualizada.getNombre());
        assertEquals(0, actualizada.getPrecio().compareTo(new BigDecimal("500.00")));

        verify(repository).findById(1);
        verify(repository).save(any(CategoriaHabitacion.class));
    }

    @Test
    @DisplayName("updateCategoria: retorna Optional.empty() si no existe")
    void updateCategoria_noExiste() {
        CategoriaHabitacion cambios = CategoriaHabitacion.builder()
            .nombre("Suite Deluxe")
            .build();

        when(repository.findById(42)).thenReturn(Optional.empty());

        Optional<CategoriaHabitacion> opt = service.updateCategoria(42, cambios);

        assertTrue(opt.isEmpty());
        verify(repository).findById(42);
        verify(repository, never()).save(any());
    }


    


}
