package PruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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


@ExtendWith(MockitoExtension.class)
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

    @DisplayName("selectAll: retorna lista con usuarios")
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

        // EXTRA: Capturamos lo que realmente se envió al repositorio.save().
        // Esto comprueba que el servicio pasó los datos correctos al DAO.
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture()); // verifica llamada y captura arg.
        Usuario enviado = captor.getValue();
        assertEquals("Prueba", enviado.getNombre(), "El nombre enviado al repositorio debe coincidir");
        
    }

}
