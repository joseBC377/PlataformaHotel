package PruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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


}
