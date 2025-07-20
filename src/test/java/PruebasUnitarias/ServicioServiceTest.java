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

    @Test
    @DisplayName("insertServicio: guarda y devuelve servicio")
    void insertServicio_ok() {
        when(repository.save(any(Servicio.class))).thenReturn(servicioBase);

        Servicio guardado = service.insertServicio(servicioBase);

        assertNotNull(guardado);
        assertEquals("Spa Premium", guardado.getNombre());
        assertEquals(0, guardado.getPrecio().compareTo(new BigDecimal("150.00")));

        ArgumentCaptor<Servicio> captor = ArgumentCaptor.forClass(Servicio.class);
        verify(repository).save(captor.capture());
        assertEquals("Spa Premium", captor.getValue().getNombre());
    }

    @Test
    @DisplayName("updateServicio: actualiza cuando existe")
    void updateServicio_ok() {
        Servicio cambios = Servicio.builder()
            .nombre("Spa Deluxe")
            .descripcion("Acceso premium con masajes")
            .precio(new BigDecimal("200.00"))
            .imagen("deluxe.png")
            .build();

        when(repository.findById(1)).thenReturn(Optional.of(servicioBase));
        when(repository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Servicio> opt = service.updateServicio(1, cambios);

        assertTrue(opt.isPresent());
        Servicio actualizado = opt.get();
        assertEquals("Spa Deluxe", actualizado.getNombre());
        assertEquals(0, actualizado.getPrecio().compareTo(new BigDecimal("200.00")));

        verify(repository).findById(1);
        verify(repository).save(any(Servicio.class));
    }


    @Test
    @DisplayName("updateServicio: retorna Optional.empty() si no existe")
    void updateServicio_noExiste() {
        Servicio cambios = Servicio.builder().nombre("Spa Deluxe").build();

        when(repository.findById(42)).thenReturn(Optional.empty());

        Optional<Servicio> opt = service.updateServicio(42, cambios);

        assertTrue(opt.isEmpty());
        verify(repository).findById(42);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("deleteServicio: elimina cuando existe y retorna true")
    void deleteServicio_ok() {
        when(repository.existsById(1)).thenReturn(true);

        boolean resultado = service.deleteServicio(1);

        assertTrue(resultado);
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deleteServicio: retorna false cuando no existe")
    void deleteServicio_noExiste() {
        when(repository.existsById(99)).thenReturn(false);

        boolean resultado = service.deleteServicio(99);

        assertFalse(resultado);
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(any());
    }

}
