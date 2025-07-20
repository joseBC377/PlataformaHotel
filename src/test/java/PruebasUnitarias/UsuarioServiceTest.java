package PruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.services.UsuarioService;
import com.example.hotel.util.Rol;


@ExtendWith(MockitoExtension.class) //Importantisimo con esto habilito el soporte de Mockito
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository repository; // simular acceso a datos

    @InjectMocks
    UsuarioService service; // se inyecta el mock en el servicio

    private Usuario usuarioBase;

    @BeforeEach
    void setUp() {
        usuarioBase = Usuario.builder()
            .id(1)
            .nombre("Prueba")
            .apellido("ApellidoTest")
            .correo("[email protected]")
            .telefono("123456789")
            .password("12345678")
            .rol(Rol.ADMIN)
            .build();
    }

    @DisplayName("Test de retornar lista con usuarios")
    @Test
    public void todosUsuarios(){
        when(repository.findAll()).thenReturn(Arrays.asList(usuarioBase));//Devolvera la lista con un unico elemento, en este caso con usuarioBase
        List<Usuario> resultado = service.selectAll();//Cuando llame al servicio solo debe devolverme ese usuarioBase

        System.out.println(resultado);
        assertEquals(1, resultado.size(), "Debe retornar 1 usuario");
        assertEquals("Prueba", resultado.get(0).getNombre());
        verify(repository, times(1)).findAll();
    }


    @DisplayName("Test de insertar un usuario")
    @Test
    public void testInsertUsuario(){
        
        when(repository.save(any(Usuario.class))).thenReturn(usuarioBase);

        Usuario resultado = service.insertUsuario(usuarioBase);

        // ASSERT: Validamos el valor retornado.
        assertNotNull(resultado, "El servicio debe retornar el usuario guardado");
        assertEquals(usuarioBase.getId(), resultado.getId(), "Id debe coincidir");
        assertEquals("Prueba", resultado.getNombre(), "Nombre debe coincidir");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture()); // verifico llamada y captura arg.
        Usuario enviado = captor.getValue();
        assertEquals("Prueba", enviado.getNombre(), "El nombre enviado al repositorio debe coincidir");
        
    }

    
    @Test
    @DisplayName("selectId: encuentra usuario existente")
    void selectId_existente() {
        when(repository.findById(1)).thenReturn(Optional.of(usuarioBase));

        Usuario resultado = service.selectId(1);

        assertEquals(1, resultado.getId()); //Compruebo que el Usuario que obtuve realmente tiene id=1. Si el resultado tiene otro id, el test fallaría., DETERMINA SI LA PRUEBA FALLO O PASO
        verify(repository).findById(1);
    }

    @Test
    @DisplayName("selectId: lanza excepción si no existe")
    void selectId_noExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.selectId(99));
        assertTrue(ex.getMessage().contains("No existe el id"));
        verify(repository).findById(99);
    }

    @Test
    @DisplayName("updateUsuario: actualiza cuando existe")
    void updateUsuario_ok() {
        // suponiendo que servicio valida por id del método
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any(Usuario.class))).thenReturn(usuarioBase);

        Usuario actualizado = service.updateUsuario(1, usuarioBase);

        assertEquals(1, actualizado.getId());
        verify(repository).existsById(1);
        verify(repository).save(usuarioBase);
    }

    @Test
    @DisplayName("updateUsuario: lanza excepción si no existe")
    void updateUsuario_noExiste() {
        when(repository.existsById(1)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateUsuario(1, usuarioBase));
        assertTrue(ex.getMessage().contains("no existe") || ex.getMessage().contains("No se puede actualizar"));
        verify(repository).existsById(1);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("deleteUsuario: elimina cuando existe")
    void deleteUsuario_ok() {
        when(repository.existsById(1)).thenReturn(true);

        service.deleteUsuario(1);

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deleteUsuario: lanza excepción si no existe")
    void deleteUsuario_noExiste() {
        when(repository.existsById(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteUsuario(99));
        assertTrue(ex.getMessage().contains("no exite") || ex.getMessage().contains("No existe"));
        verify(repository).existsById(99);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("findByCorreo: retorna usuario cuando existe")
    void findByCorreo_ok() {
        when(repository.findByCorreo(eq("[email protected]"))).thenReturn(Optional.of(usuarioBase));

        Usuario encontrado = service.findByCorreo("[email protected]");

        assertEquals(1, encontrado.getId());
        verify(repository).findByCorreo("[email protected]");
    }

    @Test
    @DisplayName("findByCorreo: lanza excepción si no existe")
    void findByCorreo_noExiste() {
        when(repository.findByCorreo(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findByCorreo("[email protected]"));
        verify(repository).findByCorreo("[email protected]");
    }


}
