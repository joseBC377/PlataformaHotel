package PruebasUnitarias;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.repositories.CategoriaHabitacionRepository;
import com.example.hotel.repositories.HabitacionRepository;
import com.example.hotel.services.HabitacionService;

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

}
