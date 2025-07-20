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

import com.example.hotel.entities.Servicio;
import com.example.hotel.repositories.ServicioRepository;
import com.example.hotel.services.ServicioService;

@ExtendWith(MockitoExtension.class)
public class ServicioServiceTest {

    @Mock
    private ServicioRepository repository;

    @InjectMocks
    private ServicioService service;

    private Servicio servicioBase;

    @BeforeEach
    void setUp() {
        servicioBase = Servicio.builder()
                .id_servicio(1)
                .nombre("Spa Premium")
                .descripcion("Acceso completo al spa")
                .precio(new BigDecimal("150.00"))
                .imagen("spa.png")
                .build();
    }


    @Test
    @DisplayName("sellectAllServicios: retorna lista con registros")
    void sellectAllServicios_ok() {
        when(repository.findAll()).thenReturn(List.of(servicioBase));

        List<Servicio> lista = service.sellectAllServicios();

        assertEquals(1, lista.size());
        assertEquals("Spa Premium", lista.get(0).getNombre());
        verify(repository).findAll();
    }


    @Test
    @DisplayName("getServicioById: presente")
    void getServicioById_presente() {
        when(repository.findById(1)).thenReturn(Optional.of(servicioBase));

        Optional<Servicio> opt = service.getServicioById(1);

        assertTrue(opt.isPresent());
        assertEquals("Spa Premium", opt.get().getNombre());
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("getServicioById: vacío cuando no existe")
    void getServicioById_vacio() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Optional<Servicio> opt = service.getServicioById(99);

        assertTrue(opt.isEmpty());
        verify(repository).findById(99);
    }


}
