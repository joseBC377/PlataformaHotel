package PruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.example.hotel.entities.Habitacion;
import com.example.hotel.repositories.CategoriaHabitacionRepository;
import com.example.hotel.repositories.HabitacionRepository;
import com.example.hotel.services.HabitacionService;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceTest {

    @Mock
    private HabitacionRepository repository;

    @Mock
    private CategoriaHabitacionRepository categoriaRepository;

    @InjectMocks
    private HabitacionService service;

    private CategoriaHabitacion categoriaBase;
    private Habitacion habitacionBase;

    @BeforeEach
    void setUp() {
        categoriaBase = CategoriaHabitacion.builder()
                .id(10)
                .nombre("Suite")
                .descripcion("Categoría Suite")
                .capacidad(4)
                .precio(new BigDecimal("350.00"))
                .imagen(null)
                .build();

        habitacionBase = Habitacion.builder()
                .id(1)
                .nombre("Hab101")
                .descripcion("Habitación amplia con vista al mar")
                .estado("DISPONIBLE")
                .categoriaHabitacion(categoriaBase)
                .build();
    }

    @Test
    @DisplayName("selectAllHabitacions: retorna lista con habitaciones")
    public void selectAllHabitacions() {
        when(repository.findAll()).thenReturn(List.of(habitacionBase));

        List<Habitacion> resultado = service.selectAllHabitacions();

        assertEquals(1, resultado.size());
        assertEquals("Hab101", resultado.get(0).getNombre());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("getHabitacionById: presente")
    void getHabitacionById_presente() {
        when(repository.findById(1)).thenReturn(Optional.of(habitacionBase));

        Optional<Habitacion> opt = service.getHabitacionById(1);

        assertTrue(opt.isPresent());
        assertEquals("Hab101", opt.get().getNombre());
        verify(repository).findById(1);
    }


    @Test
    @DisplayName("getHabitacionById: vacío cuando no existe")
    void getHabitacionById_vacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Optional<Habitacion> opt = service.getHabitacionById(99);

        assertTrue(opt.isEmpty());
        verify(repository).findById(99);
    }

    
    @Test
    @DisplayName("insertHabitacion: guarda y devuelve la habitación")
    void insertHabitacion_ok() {
        when(repository.save(any(Habitacion.class))).thenReturn(habitacionBase);

        Habitacion guardada = service.insertHabitacion(habitacionBase);

        assertNotNull(guardada);
        assertEquals("Hab101", guardada.getNombre());
        assertEquals("DISPONIBLE", guardada.getEstado());
        assertEquals(10, guardada.getCategoriaHabitacion().getId());

        ArgumentCaptor<Habitacion> captor = ArgumentCaptor.forClass(Habitacion.class);
        verify(repository).save(captor.capture());
        Habitacion enviada = captor.getValue();
        assertEquals("Hab101", enviada.getNombre());
    }



    @Test
    @DisplayName("updateHabitacion: actualiza campos sin cambiar categoría")
    void updateHabitacion_ok_sinCambiarCategoria() {
        Habitacion cambios = Habitacion.builder()
            .nombre("Hab101-Editada")
            .descripcion("Editada")
            .estado("OCUPADA")
            .build();

        when(repository.findById(1)).thenReturn(Optional.of(habitacionBase));
        when(repository.save(any(Habitacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Habitacion> opt = service.updateHabitacion(1, cambios);

        assertTrue(opt.isPresent());
        Habitacion actualizada = opt.get();
        assertEquals("Hab101-Editada", actualizada.getNombre());
        assertEquals("OCUPADA", actualizada.getEstado());
        // categoría se mantiene
        assertNotNull(actualizada.getCategoriaHabitacion());
        assertEquals(10, actualizada.getCategoriaHabitacion().getId());

        verify(repository).findById(1);
        verify(categoriaRepository, never()).findById(any());
        verify(repository).save(any(Habitacion.class));
    }


    @Test
    @DisplayName("updateHabitacion: cambia categoría cuando se envía ID válido")
    void updateHabitacion_ok_conCategoriaNueva() {
        CategoriaHabitacion nuevaCat = CategoriaHabitacion.builder()
            .id(20)
            .nombre("Doble")
            .descripcion("Dos camas")
            .capacidad(2)
            .precio(new BigDecimal("180.00"))
            .imagen(null)
            .build();

        Habitacion cambios = Habitacion.builder()
            .nombre("Hab101-Editada")
            .descripcion("Editada con cat nueva")
            .estado("MANTENIMIENTO")
            .categoriaHabitacion(nuevaCat)
            .build();

        when(repository.findById(1)).thenReturn(Optional.of(habitacionBase));
        when(categoriaRepository.findById(20)).thenReturn(Optional.of(nuevaCat));
        when(repository.save(any(Habitacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Habitacion> opt = service.updateHabitacion(1, cambios);

        assertTrue(opt.isPresent());
        Habitacion actualizada = opt.get();
        assertEquals(20, actualizada.getCategoriaHabitacion().getId());
        assertEquals("Doble", actualizada.getCategoriaHabitacion().getNombre());

        verify(repository).findById(1);
        verify(categoriaRepository).findById(20);
        verify(repository).save(any(Habitacion.class));
    }

    
    @Test
    @DisplayName("deleteHabitacion: elimina cuando existe y retorna true")
    void deleteHabitacion_ok() {
        when(repository.existsById(1)).thenReturn(true);

        boolean resultado = service.deleteHabitacion(1);

        assertTrue(resultado);
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deleteHabitacion: retorna false cuando no existe")
    void deleteHabitacion_noExiste() {
        when(repository.existsById(99)).thenReturn(false);

        boolean resultado = service.deleteHabitacion(99);

        assertFalse(resultado);
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(any());
    }

}
